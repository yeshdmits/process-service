package com.yeshenko.process.domain.enumeration;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TaskStatusEnum {
  IN_PROGRESS("In progress"),
  COMPLETED("Completed"),
  REJECTED("Rejected");

  private final String value;

  TaskStatusEnum(String value) {
    this.value = value;
  }

  public static TaskStatusEnum fromValue(String taskStatus) {
    return Arrays.stream(TaskStatusEnum.values())
        .filter(i -> i.value.equalsIgnoreCase(taskStatus))
        .findFirst()
        .orElseThrow();
  }
}
