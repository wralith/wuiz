package com.example.wuiz.quiz;

import com.example.wuiz.quiz.exception.QuizNotFoundException;
import com.example.wuiz.quiz.request.SubmitRequest;
import com.example.wuiz.result.Result;
import com.example.wuiz.result.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
  QuizRepository quizRepository;
  ResultService resultService;

  public List<Quiz> findAll() {
    return quizRepository.findAll();
  }

  public Optional<Quiz> findById(Integer id) {
    return quizRepository.findById(id);
  }

  public Result submit(SubmitRequest dto) throws QuizNotFoundException {
    Quiz quiz = this.findById(dto.getQuizId()).orElseThrow(QuizNotFoundException::new);
    int maxScore = 100; // INFO: Will be replaced with something makes sense
    int oneQuestionWeight = maxScore / quiz.getQuestions().size();

    return resultService.createResult(dto, quiz, oneQuestionWeight);
  }
}
