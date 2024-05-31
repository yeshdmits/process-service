package com.yeshenko.processservicemock.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

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
        stubPdfResponse();
        stubCorsResponse();
    }

    private void stubPdfResponse() {
        try (InputStream stream = MockApplicationListener.class.getClassLoader().getResourceAsStream("mock.pdf")) {
            if (stream != null) {
                server.stubFor(get("/api/v1/process/document?documentId=c75236b4-061c-4075-b3ae-89fde6f4f999")
                        .willReturn(aResponse()
                                .withHeader("Access-Control-Allow-Origin", "*")
                                .withHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                                .withHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                                .withStatus(200)
                                .withBody(stream.readAllBytes())));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void stubCorsResponse() {
        server.stubFor(options(UrlPattern.ANY)
                .willReturn(aResponse()
                        .withHeader("Access-Control-Allow-Origin", "*")
                        .withHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                        .withHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                        .withStatus(200)
                        .withBody("")));
    }
}
