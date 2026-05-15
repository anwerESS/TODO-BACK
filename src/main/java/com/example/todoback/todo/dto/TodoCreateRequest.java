package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.TodoPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record TodoCreateRequest(
        @NotBlank(message = "title is required")
        @Size(max = 160, message = "title must be at most 160 characters")
        String title,

        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,

        Boolean completed,

        @NotNull(message = "priority is required")
        TodoPriority priority,

        @Size(max = 80, message = "category must be at most 80 characters")
        String category,

        Instant dueDate
) {
}
