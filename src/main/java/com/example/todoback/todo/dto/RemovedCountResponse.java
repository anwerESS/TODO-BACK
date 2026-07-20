package com.example.todoback.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bulk delete result")
public record RemovedCountResponse(long removedCount) {
}
