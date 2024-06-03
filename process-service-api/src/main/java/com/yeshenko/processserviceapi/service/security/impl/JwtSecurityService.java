package com.yeshenko.processserviceapi.service.security.impl;

import com.yeshenko.processserviceapi.exception.ForbiddenException;
import com.yeshenko.processserviceapi.service.security.SecurityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = {"spring.security.type"}, havingValue = "jwt")
public class JwtSecurityService implements SecurityService {
    @Override
    public String getAccountId() {
        return ((Jwt) getAuth().getPrincipal()).getClaimAsString("preferred_username");
    }

    @Override
    public String getAccountFullName() {
        return ((Jwt) getAuth().getPrincipal()).getClaimAsString("name");
    }

    @Override
    public List<String> getCustomRole() {
        List<String> roles = ((Jwt) getAuth().getPrincipal()).getClaimAsStringList("roles");
        return roles.stream().filter(role -> "client".equals(role) || "manager".equals(role)).toList();
    }

    @Override
    public Object getUserClaims() {
        return ((Jwt) getAuth().getPrincipal()).getClaims();
    }

    private Authentication getAuth() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ForbiddenException("Unauthorized");
        }
        return authentication;
    }
}
