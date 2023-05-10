package com.example.wuiz.quiz;

import com.example.wuiz.quiz.exception.QuizNotFoundException;
import com.example.wuiz.quiz.request.CreateQuizRequest;
import com.example.wuiz.quiz.request.SubmitRequest;
import com.example.wuiz.quiz.response.QuizResponse;
import com.example.wuiz.result.Result;
import com.example.wuiz.result.response.ResultResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/quizzes")
public class QuizController {
  private QuizService service;

  @GetMapping
  public ResponseEntity<List<QuizResponse>> findAll() {
    var quizzes = service.findAll();
    return ResponseEntity.ok(quizzes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuizResponse> findById(@PathVariable Integer id) {
    var quiz = service.findById(id);
    return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/submit")
  public ResponseEntity<ResultResponse> submit(@Valid @RequestBody SubmitRequest request)
      throws QuizNotFoundException {
    Result result = service.submit(request);
    return ResponseEntity.ok(new ResultResponse(result));
  }

  @PostMapping
  public ResponseEntity<QuizResponse> create(@Valid @RequestBody CreateQuizRequest request) {
    return new ResponseEntity<>(service.createQuiz(request), HttpStatus.CREATED);
  }
}
