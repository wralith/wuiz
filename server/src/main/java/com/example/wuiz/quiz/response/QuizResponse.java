package com.example.wuiz.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class QuizResponse {
  private Integer id;
  private String title;
  private List<QuestionResponse> questions;
}
