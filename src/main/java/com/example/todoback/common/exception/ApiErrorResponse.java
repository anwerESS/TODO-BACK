package com.example.todoback.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Map;
import lombok.Builder;

@Builder
@Schema(description = "Standard API error response")
public record ApiErrorResponse(
        @Schema(description = "Error timestamp", example = "2026-07-20T09:00:00Z")
        Instant timestamp,

        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "HTTP status reason", example = "Bad Request")
        String error,

        @Schema(description = "Human-readable error message", example = "Request validation failed")
        String message,

        @Schema(description = "Request path", example = "/api/todos")
        String path,

        @Schema(description = "Field validation errors keyed by field name")
        Map<String, String> validationErrors
) {
}
