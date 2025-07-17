package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.application.dto.request.LoginRequestDto;
import com.pragma.plazacomida.application.dto.response.LoginResponseDto;
import com.pragma.plazacomida.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    
    private final IAuthHandler authHandler;
    
    @Operation(summary = "Iniciar sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authHandler.login(loginRequestDto));
    }
} 