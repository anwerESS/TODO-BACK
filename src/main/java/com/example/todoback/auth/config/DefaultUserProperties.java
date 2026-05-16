package com.example.todoback.auth.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.security.default-user")
public record DefaultUserProperties(
        @NotBlank String username,
        @NotBlank String password
) {
}
