package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.LoginRequest;
import com.pragma.plazoleta.application.dto.response.AuthTokensResponse;
import com.pragma.plazoleta.application.dto.request.RefreshTokenRequest;
import com.pragma.plazoleta.application.dto.response.AccessTokenResponse;
import com.pragma.plazoleta.infrastructure.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokensResponse> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Map<String, String> tokens = authenticationService.loginWithTokens(email, password);
        AuthTokensResponse response = new AuthTokensResponse(tokens.get("token"), tokens.get("refreshToken"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh access token",
        description = "Receives a valid refresh token and returns a new access token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "New access token generated",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"token\": \"nuevo_access_token_jwt\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid or expired refresh token"
        )
    })
    public ResponseEntity<AccessTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String token = authenticationService.refresh(refreshToken);
        return ResponseEntity.ok(new AccessTokenResponse(token));
    }
} 