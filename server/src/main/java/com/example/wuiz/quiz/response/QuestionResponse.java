package com.example.wuiz.quiz.response;

// INFO: If we want to show a single question in future, we might need a back reference to quiz

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class QuestionResponse {
  private Integer id;
  private String text;
  private List<OptionResponse> options;
}
