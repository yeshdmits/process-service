package com.yeshenko.process;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.yeshenko.process.config.AbstractIntegrationTest;
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
