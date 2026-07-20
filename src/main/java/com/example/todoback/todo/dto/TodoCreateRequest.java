package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.TodoPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Schema(description = "Todo creation payload")
public record TodoCreateRequest(
        @Schema(description = "Todo title", example = "Connect Angular service", maxLength = 160)
        @NotBlank(message = "title is required")
        @Size(max = 160, message = "title must be at most 160 characters")
        String title,

        @Schema(description = "Todo details", example = "Use HttpClient instead of localStorage", maxLength = 2000)
        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,

        @Schema(description = "Initial completion state", example = "false")
        Boolean completed,

        @Schema(description = "Todo priority", example = "HIGH")
        @NotNull(message = "priority is required")
        TodoPriority priority,

        @Schema(description = "Todo category", example = "API", maxLength = 80)
        @Size(max = 80, message = "category must be at most 80 characters")
        String category,

        @Schema(description = "Optional due date", example = "2026-07-21T09:00:00Z")
        Instant dueDate
) {
}
