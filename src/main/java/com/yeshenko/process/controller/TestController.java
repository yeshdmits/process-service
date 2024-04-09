package com.yeshenko.process.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeshenko.process.domain.repository.TestDataRepository;
import com.yeshenko.process.service.MqService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TestDataRepository testDataRepository;
  private final MqService mqService;
  private final ObjectMapper objectMapper;

  @GetMapping("/test")
  public ResponseEntity<String> ok() {
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/test/db")
  public ResponseEntity<Boolean> db() {
    return ResponseEntity.ok(
        testDataRepository.findById(0L).orElseThrow().getValue());
  }

  @GetMapping("/test/mq")
  @SneakyThrows
  public ResponseEntity<Boolean> mq() {
    var data = testDataRepository.findById(0L).orElseThrow();
    data.setValue(!data.getValue());
    mqService.sendMessageToMQ(objectMapper.writeValueAsString(data));
    return ResponseEntity.ok().build();
  }
}
