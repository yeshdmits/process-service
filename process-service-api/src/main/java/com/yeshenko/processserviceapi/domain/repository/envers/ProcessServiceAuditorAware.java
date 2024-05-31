package com.yeshenko.processserviceapi.domain.repository.envers;

import com.yeshenko.processserviceapi.service.security.SecurityService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProcessServiceAuditorAware implements AuditorAware<String> {
    private final SecurityService securityService;

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(securityService.getAccountId());
    }
}
