package com.yeshenko.process.domain.enumeration;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum DocumentStatusEnum {
  CREATED("CREATED"),
  SENT("SENT"),
  COMPLETED("COMPLETED"),
  WITHDRAWN("WITHDRAWN");

  private final String value;

  DocumentStatusEnum(String value) {
    this.value = value;
  }

  public static DocumentStatusEnum fromValue(String taskStatus) {
    return Arrays.stream(DocumentStatusEnum.values())
        .filter(i -> i.value.equalsIgnoreCase(taskStatus))
        .findFirst()
        .orElseThrow();
  }
}
