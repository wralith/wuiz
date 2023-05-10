package com.example.wuiz.quiz;

import com.example.wuiz.quiz.exception.QuizNotFoundException;
import com.example.wuiz.quiz.request.CreateQuizRequest;
import com.example.wuiz.quiz.request.SubmitRequest;
import com.example.wuiz.result.Result;
import com.example.wuiz.result.ResultService;
import jakarta.validation.Valid;
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

  public Quiz createQuiz(@Valid CreateQuizRequest dto) {
    var quiz = Quiz.builder().title(dto.getTitle()).build();

    dto.getQuestions()
        .forEach(
            dtoQuestion -> {
              var question = Question.builder().text(dtoQuestion.getText()).quiz(quiz).build();
              var dtoOptions = dtoQuestion.getOptions();
              dtoOptions.forEach(
                  o -> {
                    var option = Option.builder().question(question).text(o.getText()).build();
                    question.addOption(option);

                    if (option.getText().equals(dtoQuestion.getCorrectOption().getText())) {
                        question.setCorrectOption(option);
                    }
                  });

              quiz.addQuestion(question);
            });

    quizRepository.save(quiz);
    return quiz;
  }
}
