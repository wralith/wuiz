package com.example.wuiz.result;

import com.example.wuiz.result.response.ResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/results")
public class ResultController {
  private ResultService service;

  @GetMapping("/{id}")
  public ResponseEntity<ResultResponse> findById(@PathVariable int id) {
    var result = service.findById(id);
    if (result.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var response = new ResultResponse(result.get());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/owner/{owner}")
  public List<ResultResponse> findByOwner(@PathVariable String owner) {
    return service.findByOwner(owner).stream()
        .map(ResultResponse::new)
        .collect(Collectors.toList());
  }
}
