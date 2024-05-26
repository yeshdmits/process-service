package com.yeshenko.processserviceui.controller;

import com.yeshenko.processserviceui.backend.HandleRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Component
public class ForwardingHandler {

    private static final String FORWARD_SEGMENT = "backend";

    private final HandleRequestService handleRequestService;

    public ForwardingHandler(HandleRequestService handleRequestService) {
        this.handleRequestService = handleRequestService;
    }

    @Bean
    public RouterFunction<ServerResponse> mirror() {
        var routePattern = "/" + FORWARD_SEGMENT + "/**";
        return route().GET(routePattern, handleRequestService::handleRequest)
                .DELETE(routePattern, handleRequestService::handleRequest)
                .HEAD(routePattern, handleRequestService::handleRequest)
                .OPTIONS(routePattern, handleRequestService::handleRequest)
                .PATCH(routePattern, handleRequestService::handleRequest)
                .POST(routePattern, handleRequestService::handleRequest)
                .PUT(routePattern, handleRequestService::handleRequest)
                .build();
    }
}
