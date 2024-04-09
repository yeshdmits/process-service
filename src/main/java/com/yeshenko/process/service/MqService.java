package com.yeshenko.process.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeshenko.process.domain.entity.TestData;
import com.yeshenko.process.domain.repository.TestDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqService {

  @Value("${ibm.mq.sendingQueueName}")
  private String sendQueueName;

  private final JmsTemplate jmsTemplate;
  private final TestDataRepository testDataRepository;
  private final ObjectMapper objectMapper;

  @JmsListener(destination = "${ibm.mq.receivingQueueName}")
  @SneakyThrows
  public void readMessageFromMq(String message) {
    log.info(message);
    testDataRepository.save(objectMapper.readValue(message, TestData.class));
  }

  @SneakyThrows
  public void sendMessageToMQ(String mqString) {
    jmsTemplate.convertAndSend(sendQueueName, mqString);
  }
}
