package com.yeshenko.processserviceapi.domain.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MapUtil {

  private MapUtil() {
  }

  private static final ObjectMapper MAPPER = JsonMapper.builder()
      .addModule(new JavaTimeModule())
      .build();


  public static String serializeObjectToString(Object o) {
    try {
      return MAPPER.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      var errorMsg = "Error during serialization from object to string";
//      log
      throw new RuntimeException(errorMsg);
    }
  }

  public static <T> T serializeObjectFromString(String o, Class<T> valueType) {
    try {
      return MAPPER.readValue(o, valueType);
    } catch (Exception e) {
//      log
      throw new RuntimeException(e);
    }
  }
}
