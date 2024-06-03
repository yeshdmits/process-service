package com.yeshenko.processserviceui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final KeyCloakLogoutHandler keyCloakLogoutHandler;

    public SecurityConfig(KeyCloakLogoutHandler keyCloakLogoutHandler) {
        this.keyCloakLogoutHandler = keyCloakLogoutHandler;
    }

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", false).failureUrl("/failed"));
        http.logout(logout -> logout.addLogoutHandler(keyCloakLogoutHandler).logoutSuccessUrl("/"));
        return http.build();
    }
}
