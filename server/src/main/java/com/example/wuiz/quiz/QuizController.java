package com.example.wuiz.quiz;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/quizzes")
public class QuizController {
  QuizRepository repository;

  @GetMapping("/{id}")
  public ResponseEntity<Quiz> findById(@PathVariable Integer id) {
    var quiz = repository.findById(id);
    return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
