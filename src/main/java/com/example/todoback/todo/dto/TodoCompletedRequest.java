package com.example.todoback.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Todo completion update")
public record TodoCompletedRequest(
        @Schema(description = "Target completion state", example = "true")
        @NotNull(message = "completed is required")
        Boolean completed
) {
}
