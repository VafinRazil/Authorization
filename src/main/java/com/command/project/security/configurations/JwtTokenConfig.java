package com.command.project.security.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class JwtTokenConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;

    public String getSecret() {
        return secret;
    }

    public Duration getLifetime() {
        return lifetime;
    }
}
