package com.yeshenko.processserviceui.backend;

import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

@Service
public class HandleRequestService {
    private static final Logger logger = LoggerFactory.getLogger(HandleRequestService.class);
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
            HttpHeaders httpHeaders = obtainHeaders(serverRequest.headers().asHttpHeaders().toSingleValueMap());
            if (serverRequest.method().equals(HttpMethod.GET)) {
                requestEntity = new HttpEntity<>(httpHeaders);
            } else {
                requestEntity = new HttpEntity<>(serverRequest.body(byte[].class), httpHeaders);
            }

            URI uri = buildURI(serverRequest.uri());
            logger.info(requestEntity.toString());
            ResponseEntity<?> responseEntity = restTemplate.exchange(uri, serverRequest.method(), requestEntity, byte[].class);
            logger.info(responseEntity.toString());
            var builder = ServerResponse.status(responseEntity.getStatusCode())
                    .headers(h -> {
                        responseEntity.getHeaders().forEach((key, value) -> {
                            if (!key.equals("Transfer-Encoding")) {
                                h.add(key, value.get(0));
                            }
                        });
                    });

            if (responseEntity.getBody() != null) {
                return builder.body(responseEntity.getBody());
            }

            return builder.build();
        } catch (ServletException | IOException e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (HttpClientErrorException e) {
            logger.info(e.getMessage(), e);

            var builder = ServerResponse.status(e.getStatusCode().value());

            if (e.getResponseHeaders() != null) {
                builder.headers(headers -> headers.putAll(e.getResponseHeaders()));
            }

            return builder.body(e.getResponseBodyAsByteArray());
        }
    }

    private HttpHeaders obtainHeaders(Map<String, String> requestHeaders) {
        var headers = new HttpHeaders();
        headers.setAll(requestHeaders);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorization: " + authentication);
        if (authentication != null) {
            headers.remove("Authorization");
            DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
            headers.add("Authorization", "Bearer " + principal.getIdToken().getTokenValue());
        }
        return headers;
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
