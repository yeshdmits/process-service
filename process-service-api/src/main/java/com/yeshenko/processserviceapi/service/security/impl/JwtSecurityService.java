package com.yeshenko.processserviceapi.service.security.impl;

import com.yeshenko.processserviceapi.exception.UnauthorizedException;
import com.yeshenko.processserviceapi.service.security.SecurityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = {"spring.security.type"}, havingValue = "jwt")
public class JwtSecurityService implements SecurityService {
    @Override
    public String getAccountId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }
        Jwt principal = (Jwt) authentication.getPrincipal();
        return ((Jwt) authentication.getPrincipal()).getClaimAsString("preferred_username");
    }

    @Override
    public String getAccountFullName() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }
        return ((Jwt) authentication.getPrincipal()).getClaimAsString("name");
    }
}
