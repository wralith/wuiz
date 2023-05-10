package com.example.wuiz.result.response;

import com.example.wuiz.result.Result;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse {
  private Integer id;
  private String owner;
  private int score;
  private ResultResponseQuiz quiz;

  @AllArgsConstructor
  @Data
  public static class ResultResponseQuiz {
    private Integer id;
    private String title;
  }

  public ResultResponse(Result entity) {
    this.id = entity.getId();
    this.owner = entity.getOwner();
    this.score = entity.getScore();
    this.quiz = new ResultResponseQuiz(entity.getQuiz().getId(), entity.getQuiz().getTitle());
  }
}
