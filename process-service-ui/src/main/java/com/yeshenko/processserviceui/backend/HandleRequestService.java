package com.yeshenko.processserviceui.backend;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
public class HandleRequestService {
    private final RestTemplate restTemplate;

    @Value("${mirror.backend:http://localhost:8080}")
    private String backendUrl;

    public HandleRequestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(10000))
                .setConnectTimeout(Duration.ofMillis(10000))
                .build();
    }

    public ServerResponse handleRequest(ServerRequest serverRequest) {
        HttpEntity<?> requestEntity;

        try {
            HttpHeaders httpHeaders = serverRequest.headers().asHttpHeaders();
            if (serverRequest.method().equals(HttpMethod.GET)) {
                requestEntity = new HttpEntity<>(httpHeaders);
            } else {
                requestEntity = new HttpEntity<>(serverRequest.body(byte[].class), httpHeaders);
            }

            URI uri = buildURI(serverRequest.uri());

            ResponseEntity<?> responseEntity = restTemplate.exchange(uri, serverRequest.method(), requestEntity, byte[].class);

            var builder = ServerResponse.status(responseEntity.getStatusCode())
                    .headers(headers -> headers.putAll(responseEntity.getHeaders()));

            if (responseEntity.getBody() != null) {
                return builder.body(responseEntity.getBody());
            }
            return builder.build();
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URI buildURI(URI uri) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri.toString()).build();
        var pathSegments = uriComponents.getPathSegments();
        return UriComponentsBuilder.fromUriString(backendUrl)
                .pathSegment(pathSegments.subList(1, pathSegments.size()).toArray(new String[0]))
                .query(uri.getQuery())
                .build(true).toUri();
    }

}
