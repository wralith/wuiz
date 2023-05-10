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
  private List<SubmitRequestAnswer> answers;

  @Getter
  @Setter
  @AllArgsConstructor
  public static class SubmitRequestAnswer {
    private Integer questionId;
    private String answer;
  }
}
