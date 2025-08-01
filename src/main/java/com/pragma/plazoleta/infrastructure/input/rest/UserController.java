package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management endpoints for Plaza Comida application")
public class UserController {
    
    private final IUserHandler userHandler;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",description = "User created successfully",content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation error",content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403",description = "Forbidden",content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "409",description = "Email or document already exists",content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userHandler.createUser(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Users retrieved successfully",content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "403",description = "Forbidden",content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userHandler.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE')")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "User retrieved successfully",content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404",description = "User not found",content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<UserResponse> getUserById(
        @Parameter(
            description = "User ID (UUID)",
            example = "550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
        @PathVariable UUID userId
    ) {
        UserResponse response = userHandler.getUserById(userId);
        return ResponseEntity.ok(response);
    }
} 