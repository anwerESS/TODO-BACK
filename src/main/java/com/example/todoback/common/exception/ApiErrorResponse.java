package com.example.todoback.common.exception;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
