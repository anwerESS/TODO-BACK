package com.example.todoback.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Logout result")
public record LogoutResponse(boolean loggedOut) {
}
