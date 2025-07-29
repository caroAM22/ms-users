package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.LoginRequest;
import com.pragma.plazoleta.application.dto.response.AuthTokensResponse;
import com.pragma.plazoleta.application.dto.request.RefreshTokenRequest;
import com.pragma.plazoleta.application.dto.response.AccessTokenResponse;
import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.infrastructure.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final IUserHandler userHandler;

    @PostMapping("/register")
    @Operation(summary = "Register a new customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer registered successfully", content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "409", description = "Email or document already exists")
    })
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        UserResponse response = userHandler.registerUser(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    @Operation(
        summary = "User login"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Login successful",content = @Content(schema = @Schema(implementation = AuthTokensResponse.class))),
        @ApiResponse(responseCode = "401",description = "Invalid credentials")
    })
    public ResponseEntity<AuthTokensResponse> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Map<String, String> tokens = authenticationService.loginWithTokens(email, password);
        AuthTokensResponse response = new AuthTokensResponse(tokens.get("token"), tokens.get("refreshToken"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh access token"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "New access token generated"),
        @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<AccessTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String token = authenticationService.refresh(refreshToken);
        AccessTokenResponse response = new AccessTokenResponse(token);
        return ResponseEntity.ok(response);
    }
} 