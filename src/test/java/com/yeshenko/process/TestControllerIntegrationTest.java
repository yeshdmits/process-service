package com.yeshenko.process;

import com.yeshenko.process.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TestControllerIntegrationTest extends AbstractIntegrationTest {

  @Test
  void test_ok() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/test"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("ok"));
  }

  @Test
  void test_db_ok() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/test/db"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("true"));
  }
}
