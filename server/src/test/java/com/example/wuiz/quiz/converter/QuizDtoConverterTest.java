package com.example.wuiz.quiz.converter;

import com.example.wuiz.quiz.Option;
import com.example.wuiz.quiz.Question;
import com.example.wuiz.quiz.Quiz;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizDtoConverterTest {
  private final QuizDtoConverter converter = new QuizDtoConverter();

  @Test
  void optionToResponse() {
    var text = "test";
    var option = Option.builder().text(text).build();
    var res = converter.optionToResponse(option);
    assertEquals(text, res.getText());
  }

  @Test
  void questionToResponse() {
    var text = "test";
    var question = Question.builder().text(text).build();
    var got = converter.questionToResponse(question);
    assertEquals(text, got.getText());
  }

  @Test
  void quizToResponse() {
    var title = "test title";
    var quiz = Quiz.builder().title(title).build();
    var got = converter.quizToResponse(quiz);
    assertEquals(title, got.getTitle());
  }

  @Test
  void whenQuizToResponse_AllNestedClasses_convertedToResponse() {
    var option1 = Option.builder().text("1").build();
    var option2 = Option.builder().text("1").build();
    var question =
        Question.builder()
            .text("question")
            .options(List.of(option1, option2))
            .correctOption(option2)
            .build();
    option1.setQuestion(question);
    option2.setQuestion(question);

    var quiz = Quiz.builder().title("quiz").questions(List.of(question)).build();
    question.setQuiz(quiz);

    var got = converter.quizToResponse(quiz);
    assertEquals(quiz.getTitle(), got.getTitle());
    assertEquals(converter.questionToResponse(question), got.getQuestions().get(0));
    assertEquals(
        converter.optionToResponse(option1), got.getQuestions().get(0).getOptions().get(0));
  }
}
