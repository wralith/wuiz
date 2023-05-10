package com.example.wuiz.quiz;

import com.example.wuiz.quiz.exception.QuizNotFoundException;
import com.example.wuiz.quiz.request.SubmitRequest;
import com.example.wuiz.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/quizzes")
public class QuizController {
  QuizService service;

  @GetMapping
  public ResponseEntity<List<Quiz>> findAll() {
    var quizzes = service.findAll();
    return ResponseEntity.ok(quizzes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Quiz> findById(@PathVariable Integer id) {
    var quiz = service.findById(id);
    return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/submit")
  public ResponseEntity<Result> submit(@RequestBody SubmitRequest request) throws QuizNotFoundException {
    Result result = service.submit(request);
    return ResponseEntity.ok(result);
  }
}
