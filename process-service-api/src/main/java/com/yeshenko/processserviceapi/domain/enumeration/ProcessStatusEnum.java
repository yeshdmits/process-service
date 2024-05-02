package com.yeshenko.processserviceapi.domain.enumeration;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ProcessStatusEnum {
  CREATED("Created"), IN_PROGRESS("In Progress"), ACTIVE("Active");

  private final String value;

  ProcessStatusEnum(String value) {
    this.value = value;
  }

  public static ProcessStatusEnum fromValue(String taskStatus) {
    return Arrays.stream(ProcessStatusEnum.values())
        .filter(i -> i.value.equalsIgnoreCase(taskStatus))
        .findFirst()
        .orElseThrow();
  }
}
