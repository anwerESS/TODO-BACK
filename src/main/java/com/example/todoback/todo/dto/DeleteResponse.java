package com.example.todoback.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Delete result")
public record DeleteResponse(boolean removed) {
}
