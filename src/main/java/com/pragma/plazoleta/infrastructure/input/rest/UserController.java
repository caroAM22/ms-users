package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management endpoints for Plaza Comida application")
public class UserController {
    
    private final IUserHandler userHandler;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @Operation(
        summary = "Create a new user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "message": "User must be at least 18 years old"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "message": "You are not allowed to perform this action"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email or document already exists",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "message": "Email already exists"
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String creatorRoleName = authentication.getAuthorities().stream()
            .findFirst()
            .map(org.springframework.security.core.GrantedAuthority::getAuthority)
            .orElse("");
        UserResponse response = userHandler.createUser(request, creatorRoleName);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get all users"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    value = """
                        [
                          {
                            "id": "550e8400-e29b-41d4-a716-446655440000",
                            "name": "John",
                            "lastname": "Doe",
                            "email": "john.doe@example.com",
                            "phone": "+573001234567",
                            "birthDate": "1990-01-15",
                            "roleId": "660e8400-e29b-41d4-a716-446655440001"
                          },
                          {
                            "id": "550e8400-e29b-41d4-a716-446655440001",
                            "name": "Jane",
                            "lastname": "Smith",
                            "email": "jane.smith@example.com",
                            "phone": "+573001234568",
                            "birthDate": "1985-05-20",
                            "roleId": "660e8400-e29b-41d4-a716-446655440001"
                          }
                        ]
                        """
                )
            )
        )
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userHandler.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get user by ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    value = """
                        {
                          "id": "550e8400-e29b-41d4-a716-446655440000",
                          "name": "John",
                          "lastname": "Doe",
                          "email": "john.doe@example.com",
                          "phone": "+573001234567",
                          "birthDate": "1990-01-15",
                          "roleId": "660e8400-e29b-41d4-a716-446655440001"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Not Found",
                    value = """
                        {
                          "message": "User not found"
                        }
                        """
                )
            )
        )
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