package com.yeshenko.process.service.listener.task;

import com.yeshenko.process.service.listener.task.impl.TaskCompletedListener;
import com.yeshenko.process.service.listener.task.impl.TaskCreatedListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EngineConfigurer implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

  private final TaskCompletedListener taskCompletedListener;
  private final TaskCreatedListener taskCreatedListener;

  @Override
  public void configure(SpringProcessEngineConfiguration engineConfiguration) {
    Map<String, List<FlowableEventListener>> typedEventListeners = new HashMap<>();

    typedEventListeners.put(FlowableEngineEventType.TASK_COMPLETED.name(), List.of(taskCompletedListener));
    typedEventListeners.put(FlowableEngineEventType.TASK_CREATED.name(), List.of(taskCreatedListener));

    engineConfiguration.setTypedEventListeners(typedEventListeners);
  }
}
