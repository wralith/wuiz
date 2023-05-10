package com.example.wuiz.quiz.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SubmitRequest {
  private Integer quizId;
  @NotBlank private String owner;
  private List<Answer> answers;

  @Getter
  @Setter
  @AllArgsConstructor
  public static class Answer {
    private Integer questionId;
    private String answer;
  }
}
