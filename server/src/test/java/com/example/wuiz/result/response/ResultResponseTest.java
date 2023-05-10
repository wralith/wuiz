package com.example.wuiz.result.response;

import com.example.wuiz.quiz.Quiz;
import com.example.wuiz.result.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultResponseTest {
  @Test
  void createResultResponseFromEntity() {
    Quiz quiz = Quiz.builder().title("quiz").build();
    Result result = Result.builder().owner("owner").score(100).quiz(quiz).build();

    var got = new ResultResponse(result);
    assertEquals(result.getId(), got.getId());
    assertEquals("owner", got.getOwner());
    assertEquals(100, got.getScore());
    assertEquals("quiz", got.getQuiz().getTitle());
    assertEquals(quiz.getId(), got.getQuiz().getId());
  }
}
