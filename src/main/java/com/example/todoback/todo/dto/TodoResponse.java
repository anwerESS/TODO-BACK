package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.Todo;
import com.example.todoback.todo.domain.TodoPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.Builder;

@Builder
@Schema(description = "Todo resource")
public record TodoResponse(
        @Schema(description = "Todo id", example = "1")
        Long id,

        @Schema(description = "Todo title", example = "Review API contract")
        String title,

        @Schema(description = "Todo details", example = "Confirm frontend fields match the backend payload")
        String description,

        @Schema(description = "Completion state", example = "false")
        boolean completed,

        @Schema(description = "Creation timestamp", example = "2026-07-20T08:30:00Z")
        Instant createdAt,

        @Schema(description = "Last update timestamp", example = "2026-07-20T09:15:00Z")
        Instant updatedAt,

        @Schema(description = "Todo priority", example = "HIGH")
        TodoPriority priority,

        @Schema(description = "Todo category", example = "API")
        String category,

        @Schema(description = "Optional due date", example = "2026-07-21T09:00:00Z")
        Instant dueDate
) {
    public static TodoResponse fromEntity(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .priority(todo.getPriority())
                .category(todo.getCategory())
                .dueDate(todo.getDueDate())
                .build();
    }
}
