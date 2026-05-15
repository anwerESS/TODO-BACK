package com.example.todoback.todo.controller;

import com.example.todoback.todo.dto.DeleteResponse;
import com.example.todoback.todo.dto.RemovedCountResponse;
import com.example.todoback.todo.dto.TodoCompletedRequest;
import com.example.todoback.todo.dto.TodoCreateRequest;
import com.example.todoback.todo.dto.TodoResponse;
import com.example.todoback.todo.dto.TodoUpdateRequest;
import com.example.todoback.todo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<TodoResponse> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getById(@PathVariable @Positive Long id) {
        return todoService.getById(id);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody TodoCreateRequest request) {
        TodoResponse created = todoService.create(request);
        return ResponseEntity
                .created(URI.create("/api/todos/" + created.id()))
                .body(created);
    }

    @PatchMapping("/{id}")
    public TodoResponse update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody TodoUpdateRequest request
    ) {
        return todoService.update(id, request);
    }

    @PatchMapping("/{id}/toggle")
    public TodoResponse toggleCompleted(@PathVariable @Positive Long id) {
        return todoService.toggleCompleted(id);
    }

    @PatchMapping("/{id}/completed")
    public TodoResponse setCompleted(
            @PathVariable @Positive Long id,
            @Valid @RequestBody TodoCompletedRequest request
    ) {
        return todoService.setCompleted(id, request.completed());
    }

    @DeleteMapping("/{id}")
    public DeleteResponse deleteById(@PathVariable @Positive Long id) {
        return new DeleteResponse(todoService.deleteById(id));
    }

    @DeleteMapping("/completed")
    public RemovedCountResponse deleteCompleted() {
        return new RemovedCountResponse(todoService.deleteCompleted());
    }

    @DeleteMapping
    public RemovedCountResponse clear() {
        return new RemovedCountResponse(todoService.clear());
    }
}
