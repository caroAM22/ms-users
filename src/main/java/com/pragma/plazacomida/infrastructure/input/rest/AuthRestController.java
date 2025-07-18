package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.application.dto.request.LoginRequestDto;
import com.pragma.plazacomida.application.dto.response.LoginResponseDto;
import com.pragma.plazacomida.application.handler.IAuthHandler;
import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.api.IUserServicePort;
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
    private final IAuthServicePort authServicePort;
    private final IUserServicePort userServicePort;
    
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authHandler.login(loginRequestDto));
    }
    
    @Operation(summary = "Refresh access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        // Remove "Bearer " if present
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        
        // Validate refresh token
        if (!authServicePort.validateToken(refreshToken)) {
            return ResponseEntity.status(401).build();
        }
        
        // Get user from token
        String email = authServicePort.getEmailFromToken(refreshToken);
        var user = userServicePort.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate new tokens
        String newAccessToken = authServicePort.generateToken(user);
        String newRefreshToken = authServicePort.generateRefreshToken(user);
        
        return ResponseEntity.ok(new LoginResponseDto(newAccessToken, newRefreshToken));
    }
} 