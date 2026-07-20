package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.TodoPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Schema(description = "Partial todo update payload")
public record TodoUpdateRequest(
        @Schema(description = "Todo title", example = "Review API contract", maxLength = 160)
        @Pattern(regexp = ".*\\S.*", message = "title must not be blank")
        @Size(max = 160, message = "title must be at most 160 characters")
        String title,

        @Schema(description = "Todo details", example = "Confirm frontend fields match the backend payload", maxLength = 2000)
        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,

        @Schema(description = "Completion state", example = "true")
        Boolean completed,

        @Schema(description = "Todo priority", example = "MEDIUM")
        TodoPriority priority,

        @Schema(description = "Todo category", example = "Docs", maxLength = 80)
        @Size(max = 80, message = "category must be at most 80 characters")
        String category,

        @Schema(description = "Optional due date", example = "2026-07-21T09:00:00Z")
        Instant dueDate
) {
}
