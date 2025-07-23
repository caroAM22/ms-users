package com.pragma.plazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(description = "User email", example = "user@email.com")
    private String email;
    @Schema(description = "User password", example = "password123")
    private String password;
} 