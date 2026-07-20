package com.example.todoback.todo.controller;

import com.example.todoback.common.exception.ApiErrorResponse;
import com.example.todoback.config.OpenApiConfig;
import com.example.todoback.todo.dto.DeleteResponse;
import com.example.todoback.todo.dto.RemovedCountResponse;
import com.example.todoback.todo.dto.TodoCompletedRequest;
import com.example.todoback.todo.dto.TodoCreateRequest;
import com.example.todoback.todo.dto.TodoResponse;
import com.example.todoback.todo.dto.TodoUpdateRequest;
import com.example.todoback.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Todos", description = "Todo management operations")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "List todos", description = "Returns all todos for the application.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todos returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TodoResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public List<TodoResponse> getAll() {
        return todoService.getAll();
    }

    @Operation(summary = "Get a todo", description = "Returns a todo by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo returned",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo id",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public TodoResponse getById(@Parameter(description = "Todo id", example = "1") @PathVariable @Positive Long id) {
        return todoService.getById(id);
    }

    @Operation(summary = "Create a todo", description = "Creates a new todo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Todo created",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody TodoCreateRequest request) {
        TodoResponse created = todoService.create(request);
        return ResponseEntity
                .created(URI.create("/api/todos/" + created.id()))
                .body(created);
    }

    @Operation(summary = "Update a todo", description = "Partially updates a todo by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo updated",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo id or request body",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public TodoResponse update(
            @Parameter(description = "Todo id", example = "1") @PathVariable @Positive Long id,
            @Valid @RequestBody TodoUpdateRequest request
    ) {
        return todoService.update(id, request);
    }

    @Operation(summary = "Toggle completion", description = "Toggles the completion state of a todo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo toggled",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo id",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping("/{id}/toggle")
    public TodoResponse toggleCompleted(
            @Parameter(description = "Todo id", example = "1") @PathVariable @Positive Long id
    ) {
        return todoService.toggleCompleted(id);
    }

    @Operation(summary = "Set completion", description = "Sets the completion state of a todo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo completion updated",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo id or request body",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PatchMapping("/{id}/completed")
    public TodoResponse setCompleted(
            @Parameter(description = "Todo id", example = "1") @PathVariable @Positive Long id,
            @Valid @RequestBody TodoCompletedRequest request
    ) {
        return todoService.setCompleted(id, request.completed());
    }

    @Operation(summary = "Delete a todo", description = "Deletes a todo by id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo delete result returned",
                    content = @Content(schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo id",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public DeleteResponse deleteById(
            @Parameter(description = "Todo id", example = "1") @PathVariable @Positive Long id
    ) {
        return new DeleteResponse(todoService.deleteById(id));
    }

    @Operation(summary = "Delete completed todos", description = "Deletes all completed todos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Completed todos deleted",
                    content = @Content(schema = @Schema(implementation = RemovedCountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/completed")
    public RemovedCountResponse deleteCompleted() {
        return new RemovedCountResponse(todoService.deleteCompleted());
    }

    @Operation(summary = "Delete all todos", description = "Deletes all todos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todos deleted",
                    content = @Content(schema = @Schema(implementation = RemovedCountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping
    public RemovedCountResponse clear() {
        return new RemovedCountResponse(todoService.clear());
    }
}
