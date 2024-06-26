package com.yeshenko.processservicemock;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessServiceMockApplicationTests {

  @Autowired
  ProcessServiceMockApplication processServiceMockApplication;
  @Test
  void contextLoads() {
    assertNotNull(processServiceMockApplication);
  }

}
