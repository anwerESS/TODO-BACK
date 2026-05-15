package com.example.todoback.todo.service;

import com.example.todoback.common.exception.ResourceNotFoundException;
import com.example.todoback.todo.domain.Todo;
import com.example.todoback.todo.dto.TodoCreateRequest;
import com.example.todoback.todo.dto.TodoResponse;
import com.example.todoback.todo.dto.TodoUpdateRequest;
import com.example.todoback.todo.repository.TodoRepository;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final Clock clock = Clock.systemUTC();

    @Transactional(readOnly = true)
    public List<TodoResponse> getAll() {
        return todoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(TodoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoResponse getById(Long id) {
        return TodoResponse.fromEntity(getEntityById(id));
    }

    @Transactional
    public TodoResponse create(TodoCreateRequest request) {
        Instant now = Instant.now(clock);
        Todo todo = Todo.builder()
                .title(request.title().trim())
                .description(normalizeOptionalText(request.description()))
                .completed(request.completed() != null && request.completed())
                .createdAt(now)
                .updatedAt(now)
                .priority(request.priority())
                .category(normalizeOptionalText(request.category()))
                .dueDate(request.dueDate())
                .build();

        return TodoResponse.fromEntity(todoRepository.save(todo));
    }

    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest request) {
        Todo todo = getEntityById(id);

        if (request.title() != null) {
            todo.setTitle(request.title().trim());
        }
        if (request.description() != null) {
            todo.setDescription(normalizeOptionalText(request.description()));
        }
        if (request.completed() != null) {
            todo.setCompleted(request.completed());
        }
        if (request.priority() != null) {
            todo.setPriority(request.priority());
        }
        if (request.category() != null) {
            todo.setCategory(normalizeOptionalText(request.category()));
        }
        if (request.dueDate() != null) {
            todo.setDueDate(request.dueDate());
        }

        todo.setUpdatedAt(Instant.now(clock));
        return TodoResponse.fromEntity(todo);
    }

    @Transactional
    public TodoResponse toggleCompleted(Long id) {
        Todo todo = getEntityById(id);
        todo.setCompleted(!todo.isCompleted());
        todo.setUpdatedAt(Instant.now(clock));
        return TodoResponse.fromEntity(todo);
    }

    @Transactional
    public TodoResponse setCompleted(Long id, boolean completed) {
        Todo todo = getEntityById(id);
        todo.setCompleted(completed);
        todo.setUpdatedAt(Instant.now(clock));
        return TodoResponse.fromEntity(todo);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Todo with id " + id + " was not found");
        }

        todoRepository.deleteById(id);
        return true;
    }

    @Transactional
    public long deleteCompleted() {
        long completedCount = todoRepository.countByCompletedTrue();
        if (completedCount > 0) {
            todoRepository.deleteByCompletedTrue();
        }

        return completedCount;
    }

    @Transactional
    public long clear() {
        long count = todoRepository.count();
        todoRepository.deleteAllInBatch();
        return count;
    }

    private Todo getEntityById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " was not found"));
    }

    private String normalizeOptionalText(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
