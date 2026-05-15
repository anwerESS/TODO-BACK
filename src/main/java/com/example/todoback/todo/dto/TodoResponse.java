package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.Todo;
import com.example.todoback.todo.domain.TodoPriority;
import java.time.Instant;
import lombok.Builder;

@Builder
public record TodoResponse(
        Long id,
        String title,
        String description,
        boolean completed,
        Instant createdAt,
        Instant updatedAt,
        TodoPriority priority,
        String category,
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
