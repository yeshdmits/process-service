package com.yeshenko.process.service.flowable.task;

import org.flowable.common.engine.api.delegate.event.AbstractFlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.task.api.Task;

public abstract class TaskEventListener extends AbstractFlowableEventListener {

  @Override
  public void onEvent(FlowableEvent flowableEvent) {
    if (flowableEvent instanceof FlowableEntityEvent flowableEntityEvent) {

      var entity = flowableEntityEvent.getEntity();

      if (entity instanceof Task task) {
        onTaskEvent(flowableEvent, task);
      }
    }

  }

  @Override
  public boolean isFailOnException() {
    return false;
  }

  public abstract void onTaskEvent(FlowableEvent flowableEvent, Task task);
}
