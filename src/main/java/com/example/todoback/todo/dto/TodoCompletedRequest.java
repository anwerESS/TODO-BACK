package com.example.todoback.todo.dto;

import jakarta.validation.constraints.NotNull;

public record TodoCompletedRequest(
        @NotNull(message = "completed is required")
        Boolean completed
) {
}
