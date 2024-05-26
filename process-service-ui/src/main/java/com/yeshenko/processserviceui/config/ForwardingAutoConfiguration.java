package com.yeshenko.processserviceui.config;

import com.yeshenko.processserviceui.backend.HandleRequestService;
import com.yeshenko.processserviceui.controller.ForwardingHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ForwardingAutoConfiguration{

    @ConditionalOnMissingBean
    @Bean
    public ForwardingHandler forwardingHandler(HandleRequestService handleRequestService){
        return new ForwardingHandler(handleRequestService);
    }
}
