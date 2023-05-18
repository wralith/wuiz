package com.example.wuiz.quiz.converter;

import com.example.wuiz.quiz.Option;
import com.example.wuiz.quiz.Question;
import com.example.wuiz.quiz.Quiz;
import com.example.wuiz.quiz.response.OptionResponse;
import com.example.wuiz.quiz.response.QuestionResponse;
import com.example.wuiz.quiz.response.QuizResponse;
import org.springframework.stereotype.Component;

// Info: Maybe each response dto can have their own constructor with entity?

@Component
public class QuizDtoConverter {

  public QuizResponse quizToResponse(Quiz entity) {
    var questions = entity.getQuestions().stream().map(this::questionToResponse).toList();
    return new QuizResponse(entity.getId(), entity.getTitle(), questions);
  }

  public QuestionResponse questionToResponse(Question entity) {
    var options = entity.getOptions().stream().map(this::optionToResponse).toList();
    return new QuestionResponse(entity.getId(), entity.getText(), options);
  }

  public OptionResponse optionToResponse(Option entity) {
    return new OptionResponse(entity.getId(), entity.getText());
  }
}
