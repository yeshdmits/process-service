package com.yeshenko.processserviceapi.service.security.impl;

import com.yeshenko.processserviceapi.service.security.SecurityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = {"spring.security.type"}, havingValue = "none")
public class NoneSecurityService implements SecurityService {
    @Override
    public String getAccountId() {
        return "noAuth";
    }

    @Override
    public String getAccountFullName() {
        return "No Auth";
    }
}
