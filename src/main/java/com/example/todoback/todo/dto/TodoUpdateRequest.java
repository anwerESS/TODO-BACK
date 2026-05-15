package com.example.todoback.todo.dto;

import com.example.todoback.todo.domain.TodoPriority;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record TodoUpdateRequest(
        @Pattern(regexp = ".*\\S.*", message = "title must not be blank")
        @Size(max = 160, message = "title must be at most 160 characters")
        String title,

        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,

        Boolean completed,

        TodoPriority priority,

        @Size(max = 80, message = "category must be at most 80 characters")
        String category,

        Instant dueDate
) {
}
