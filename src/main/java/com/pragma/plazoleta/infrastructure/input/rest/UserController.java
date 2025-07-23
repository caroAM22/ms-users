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

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management endpoints for Plaza Comida application")
public class UserController {
    
    private final IUserHandler userHandler;
    
    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with OWNER role. Validates age (18+), phone format, and unique email/document."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
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
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Validation Error",
                    value = """
                        {
                          "timestamp": "2024-01-15T10:30:00",
                          "status": 400,
                          "error": "Bad Request",
                          "message": "User must be at least 18 years old"
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
                    name = "Conflict Error",
                    value = """
                        {
                          "timestamp": "2024-01-15T10:30:00",
                          "status": 409,
                          "error": "Conflict",
                          "message": "Email already exists"
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userHandler.handle(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
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
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a user by ID, including their role information"
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
                          "role": {
                            "roleId": "660e8400-e29b-41d4-a716-446655440001",
                            "roleName": "OWNER"
                          }
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
                          "timestamp": "2024-01-15T10:30:00",
                          "status": 404,
                          "error": "User Not Found",
                          "message": "User not found with id: 550e8400-e29b-41d4-a716-446655440000"
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