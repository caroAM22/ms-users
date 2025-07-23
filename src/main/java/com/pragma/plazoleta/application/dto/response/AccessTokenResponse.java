package com.pragma.plazoleta.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    @Schema(description = "Access token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String token;
} 