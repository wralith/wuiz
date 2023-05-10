package com.example.wuiz;

import com.example.wuiz.quiz.Option;
import com.example.wuiz.quiz.Question;
import com.example.wuiz.quiz.Quiz;
import com.example.wuiz.quiz.QuizRepository;
import com.example.wuiz.quiz.request.SubmitRequest;
import com.example.wuiz.result.ResultRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
  @Autowired TestRestTemplate restTemplate;
  @Autowired QuizRepository quizRepository;
  @Autowired ResultRepository resultRepository;
  Quiz quiz;

  @BeforeEach
  void beforeEach() {
    Option correctOption = Option.builder().text("4").build();
    var options =
        List.of(
            Option.builder().text("1").build(),
            Option.builder().text("2").build(),
            Option.builder().text("3").build(),
            correctOption);

    Question question =
        Question.builder().text("2 + 2 = ?").options(options).correctOption(correctOption).build();
    options.forEach(o -> o.setQuestion(question));

    List<Question> questions = List.of(question);
    quiz = Quiz.builder().title("Test quiz").questions(questions).build();
    question.setQuiz(quiz);

    quizRepository.save(quiz);
  }

  @AfterEach
  void afterEach() {
    resultRepository.deleteAll();
    quizRepository.deleteAll();
  }

  @Test
  void shouldReturnAllQuizzes() {
    ResponseEntity<String> res = restTemplate.getForEntity("/quizzes", String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(res.getBody());
    Number id = documentContext.read("$[0].id");
    String title = documentContext.read("$[0].title");
    assertThat(id).isEqualTo(quiz.getId());
    assertThat(title).isEqualTo(quiz.getTitle());
  }

  @Test
  void shouldReturnQuizWhenDataExists() {
    ResponseEntity<String> res =
        restTemplate.getForEntity("/quizzes/" + quiz.getId(), String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(res.getBody());
    Number id = documentContext.read("$.id");
    String title = documentContext.read("$.title");
    assertThat(id).isEqualTo(quiz.getId());
    assertThat(title).isEqualTo(quiz.getTitle());
  }

  @Test
  void shouldReturn404WhenQuizNotExists() {
    ResponseEntity<String> res = restTemplate.getForEntity("/quizzes/99", String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldReturnResultWith100Score() {
    SubmitRequest.SubmitRequestAnswer answer = new SubmitRequest.SubmitRequestAnswer(quiz.getQuestions().get(0).getId(), "4");
    SubmitRequest submitRequest = new SubmitRequest(quiz.getId(), "test", List.of(answer));

    HttpEntity<SubmitRequest> request = new HttpEntity<>(submitRequest);
    ResponseEntity<String> res =
        this.restTemplate.postForEntity("/quizzes/submit", request, String.class);

    DocumentContext documentContext = JsonPath.parse(res.getBody());
    int score = documentContext.read("$.score");

    assertThat(score).isEqualTo(100);
  }

  @Test
  void shouldReturnResultWith0Score() {
    SubmitRequest.SubmitRequestAnswer answer = new SubmitRequest.SubmitRequestAnswer(quiz.getQuestions().get(0).getId(), "1");
    SubmitRequest submitRequest = new SubmitRequest(quiz.getId(), "test", List.of(answer));

    HttpEntity<SubmitRequest> request = new HttpEntity<>(submitRequest);
    ResponseEntity<String> res =
        this.restTemplate.postForEntity("/quizzes/submit", request, String.class);

    DocumentContext documentContext = JsonPath.parse(res.getBody());
    int score = documentContext.read("$.score");

    assertThat(score).isEqualTo(0);
  }

  @Test
  void shouldReturnResultForOwnerAndReturnResultForResultId() {
    SubmitRequest.SubmitRequestAnswer answer = new SubmitRequest.SubmitRequestAnswer(quiz.getQuestions().get(0).getId(), "4");
    SubmitRequest submitRequest = new SubmitRequest(quiz.getId(), "test", List.of(answer));
    this.restTemplate.postForEntity(
        "/quizzes/submit", new HttpEntity<>(submitRequest), String.class);

    ResponseEntity<String> res = restTemplate.getForEntity("/results/owner/test", String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(res.getBody());
    int id = documentContext.read("$[0].id");
    String owner = documentContext.read("$[0].owner");
    int score = documentContext.read("$[0].score");
    assertThat(owner).isEqualTo("test");
    assertThat(score).isEqualTo(100);

    // getById
    res = restTemplate.getForEntity("/results/" + id, String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    documentContext = JsonPath.parse(res.getBody());
    owner = documentContext.read("$.owner");
    score = documentContext.read("$.score");
    assertThat(owner).isEqualTo("test");
    assertThat(score).isEqualTo(100);
  }

  @Test
  void shouldCreateQuizAndReturnCreatedQuiz() throws JSONException {
    var json = new JSONObject();
    json.put("title", "Create quiz test");

    var questionJson = new JSONObject();
    questionJson.put("text", "What is the first letter in alphabet?");

    var questionsJson = new JSONArray();

    var optionsJson = new JSONArray();
    optionsJson.put(new JSONObject().put("text", "a"));
    optionsJson.put(new JSONObject().put("text", "g"));
    optionsJson.put(new JSONObject().put("text", "z"));

    questionJson.put("options", optionsJson);
    questionJson.put("correctOption", new JSONObject().put("text", "a"));

    questionsJson.put(questionJson);
    json.put("questions", questionsJson);

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    var res =
        restTemplate.postForEntity(
            "/quizzes", new HttpEntity<>(json.toString(), headers), String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    var documentContext = JsonPath.parse(res.getBody());

    res = restTemplate.getForEntity("/quizzes/" + documentContext.read("$.id"), String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    documentContext = JsonPath.parse(res.getBody());
    var title = documentContext.read("$.title");
    var questions = (List<?>) documentContext.read("$.questions");
    assertThat(title).isEqualTo("Create quiz test");
    assertThat(questions.size()).isEqualTo(1);
  }
}
