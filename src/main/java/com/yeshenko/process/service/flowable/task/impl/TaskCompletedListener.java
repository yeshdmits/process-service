package com.yeshenko.process.service.flowable.task.impl;

import com.yeshenko.process.service.process.TaskEntityService;
import com.yeshenko.process.service.flowable.task.TaskEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TaskCompletedListener extends TaskEventListener {

  private TaskEntityService taskEntityService;

  @Lazy
  @Autowired
  public void setTaskEntityService(TaskEntityService taskEntityService) {
    this.taskEntityService = taskEntityService;
  }

  @Override
  public void onTaskEvent(FlowableEvent flowableEvent, Task task) {
    taskEntityService.completeTask(task);
  }
}
