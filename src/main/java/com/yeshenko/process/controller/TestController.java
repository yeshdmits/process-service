package com.yeshenko.process.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeshenko.process.domain.repository.TestDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TestDataRepository testDataRepository;

  @GetMapping("/test")
  public ResponseEntity<String> ok() {
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/test/db")
  public ResponseEntity<Boolean> db() {
    return ResponseEntity.ok(
        testDataRepository.findById(0L).orElseThrow().getValue());
  }
}
