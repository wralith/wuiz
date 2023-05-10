package com.example.wuiz.result;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/results")
public class ResultController {
  private ResultService service;

  @GetMapping("/{id}")
  public ResponseEntity<Result> findById(@PathVariable int id) {
    return service
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/owner/{owner}")
  public List<Result> findByOwner(@PathVariable String owner) {
    return service.findByOwner(owner);
  }
}
