package com.pragma.plazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @Schema(description = "Refresh token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...refresh...")
    private String refreshToken;
} 