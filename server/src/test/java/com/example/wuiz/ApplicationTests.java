package com.example.wuiz;

import com.example.wuiz.quiz.Option;
import com.example.wuiz.quiz.Question;
import com.example.wuiz.quiz.Quiz;
import com.example.wuiz.quiz.QuizRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
  @Autowired TestRestTemplate restTemplate;
  @Autowired QuizRepository quizRepository;
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

    Question question = Question.builder().text("2 + 2 = ?").options(options).build();
    options.forEach(o -> o.setQuestion(question));

    List<Question> questions = List.of(question);
    quiz = Quiz.builder().title("Test quiz").questions(questions).build();
    question.setQuiz(quiz);

    quizRepository.save(quiz);
  }

  @AfterEach
  void afterEach() {
    quizRepository.deleteById(quiz.getId());
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
}
