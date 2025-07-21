package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.application.handler.IRoleHandler;
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

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "Role management endpoints for Plaza Comida application")
public class RoleController {
    
    private final IRoleHandler roleHandler;
    
    @GetMapping("/id")
    @Operation(
        summary = "Get role ID by name",
        description = "Retrieves the unique identifier (UUID) for a role based on its name. " +
                     "Available roles: ADMIN, OWNER, EMPLOYEE, CUSTOMER"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Role ID found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RoleResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    value = "{\"id\": \"550e8400-e29b-41d4-a716-446655440000\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request - role name is missing or empty",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Bad Request",
                    value = "{\"timestamp\": \"2024-01-15T10:30:00\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"Role name is required\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Role not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Not Found",
                    value = "{\"timestamp\": \"2024-01-15T10:30:00\", \"status\": 404, \"error\": \"Role Not Found\", \"message\": \"Role not found with name: INVALID_ROLE\"}"
                )
            )
        )
    })
    public ResponseEntity<RoleResponse> getRoleIdByName(
        @Parameter(
            description = "Name of the role to find (ADMIN, OWNER, EMPLOYEE, CUSTOMER)",
            example = "ADMIN",
            required = true
        )
        @Valid @ModelAttribute RoleRequest request
    ) {
        RoleResponse response = roleHandler.handle(request);
        return ResponseEntity.ok(response);
    }
} 