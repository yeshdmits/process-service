package com.yeshenko.processservicemock.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "wiremock.enabled", havingValue = "true")
public class MockApplicationListener {

  private final WireMockServer server;

  public MockApplicationListener(@Autowired WireMockServer server) {
    this.server = server;
  }

  @EventListener
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    server.start();
  }
}
