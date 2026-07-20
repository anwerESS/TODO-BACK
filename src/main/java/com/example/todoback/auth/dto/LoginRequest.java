package com.example.todoback.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login credentials")
public record LoginRequest(
        @Schema(description = "Username", example = "user")
        @NotBlank(message = "username is required")
        String username,

        @Schema(description = "Password", example = "1234", format = "password")
        @NotBlank(message = "password is required")
        String password
) {
}
