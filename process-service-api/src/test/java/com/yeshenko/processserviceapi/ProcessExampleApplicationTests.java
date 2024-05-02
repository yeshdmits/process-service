package com.yeshenko.processserviceapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.yeshenko.processserviceapi.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProcessExampleApplicationTests extends AbstractIntegrationTest {

  @Autowired
  private ProcessExampleApplication processExampleApplication;

  @Test
  void contextLoads() {
    assertNotNull(processExampleApplication);
  }
}
