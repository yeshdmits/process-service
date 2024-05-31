package com.yeshenko.processserviceapi.service.security.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = {"spring.security.type"}, havingValue = "jwt")
public class JwtSecurityService {
}
