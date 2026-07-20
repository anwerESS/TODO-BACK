package com.example.todoback.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "JWT login response")
public record LoginResponse(
        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token type", example = "Bearer")
        String tokenType,

        @Schema(description = "Seconds until the token expires", example = "3600")
        long expiresIn,

        @Schema(description = "Token expiration timestamp", example = "2026-07-20T12:00:00Z")
        Instant expiresAt,

        @Schema(description = "Authenticated username", example = "user")
        String username
) {
}
