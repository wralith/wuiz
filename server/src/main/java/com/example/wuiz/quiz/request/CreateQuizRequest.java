package com.example.wuiz.quiz.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateQuizRequest {
  @NotNull
  @Size(min = 5, max = 120)
  private String title;

  @NotEmpty private List<CreateQuizRequestQuestion> questions;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CreateQuizRequestQuestion {
    private String text;
    private List<CreateQuizRequestOption> options;
    private CreateQuizRequestOption correctOption;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CreateQuizRequestOption {
    private String text;
  }
}
