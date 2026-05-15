package com.example.todoback.todo.config;

import com.example.todoback.todo.domain.Todo;
import com.example.todoback.todo.domain.TodoPriority;
import com.example.todoback.todo.repository.TodoRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TodoDataInitializer implements CommandLineRunner {

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (todoRepository.count() > 0) {
            return;
        }

        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        todoRepository.saveAll(List.of(
                Todo.builder()
                        .title("Review Angular routing")
                        .description("Confirm home, detail, and creation routes are easy to navigate.")
                        .completed(false)
                        .createdAt(now)
                        .updatedAt(now)
                        .priority(TodoPriority.HIGH)
                        .category("Angular")
                        .dueDate(now.plus(1, ChronoUnit.DAYS))
                        .build(),
                Todo.builder()
                        .title("Add search and filters")
                        .description("Search by title and content, then filter by priority and completion.")
                        .completed(true)
                        .createdAt(now)
                        .updatedAt(now)
                        .priority(TodoPriority.MEDIUM)
                        .category("UI")
                        .build(),
                Todo.builder()
                        .title("Prepare service integration")
                        .description("Keep the TodoService API ready for a future backend connection.")
                        .completed(false)
                        .createdAt(now)
                        .updatedAt(now)
                        .priority(TodoPriority.MEDIUM)
                        .category("Architecture")
                        .dueDate(now.plus(3, ChronoUnit.DAYS))
                        .build(),
                Todo.builder()
                        .title("Polish responsive layout")
                        .description("Check the list and forms on mobile and desktop widths.")
                        .completed(false)
                        .createdAt(now)
                        .updatedAt(now)
                        .priority(TodoPriority.LOW)
                        .category("Design")
                        .build(),
                Todo.builder()
                        .title("Validate CRUD logs")
                        .description("Create, edit, and delete a todo while checking the browser console.")
                        .completed(false)
                        .createdAt(now)
                        .updatedAt(now)
                        .priority(TodoPriority.HIGH)
                        .category("Debug")
                        .dueDate(now.plus(2, ChronoUnit.DAYS))
                        .build()
        ));
    }
}
