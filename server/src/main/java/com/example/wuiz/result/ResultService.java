package com.example.wuiz.result;

import com.example.wuiz.quiz.Question;
import com.example.wuiz.quiz.Quiz;
import com.example.wuiz.quiz.request.SubmitRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResultService {
  ResultRepository repository;

  public Optional<Result> findById(int id) {
      return repository.findById(id);
  }

  public List<Result> findByOwner(String owner) {
      return repository.findByOwner(owner);
  }

  public Result createResult(SubmitRequest dto, Quiz quiz, int oneQuestionWeight) {
    Result result = new Result();
    result.setQuiz(quiz);
    result.setOwner(dto.getOwner());

    Map<Integer, Question> questions =
        quiz.getQuestions().stream()
            .collect(Collectors.toMap(Question::getId, Function.identity()));

    dto.getAnswers()
        .forEach(
            answer -> {
              var question = questions.get(answer.getQuestionId());
              var equals = question.getCorrectOption().getText().equals(answer.getAnswer());

              if (equals) {
                result.increasePoint(oneQuestionWeight);
              }
            });

    repository.save(result);
    return result;
  }
}
