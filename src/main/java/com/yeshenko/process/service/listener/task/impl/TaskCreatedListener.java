package com.yeshenko.process.service.listener.task.impl;

import com.yeshenko.process.service.TaskEntityService;
import com.yeshenko.process.service.listener.task.TaskEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TaskCreatedListener extends TaskEventListener {

  private TaskEntityService taskEntityService;

  @Lazy
  @Autowired
  public void setTaskEntityService(TaskEntityService taskEntityService) {
    this.taskEntityService = taskEntityService;
  }

  @Override
  public void onTaskEvent(FlowableEvent flowableEvent, Task task) {
    taskEntityService.createTask(task);
  }
}
