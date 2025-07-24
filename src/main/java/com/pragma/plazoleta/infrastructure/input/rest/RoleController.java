package com.pragma.plazoleta.infrastructure.input.rest;

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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "Role management endpoints for Plaza Comida application")
public class RoleController {
    
    private final IRoleHandler roleHandler;
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get role by ID",
        description = "Retrieves the complete role information (id, name, description) for a given role ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Role found successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RoleResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    value = """
                        {
                          "id": "550e8400-e29b-41d4-a716-446655440000",
                          "name": "OWNER",
                          "description": "Restaurant owner"
                        }
                        """
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
                    value = """
                        {
                          "timestamp": "2024-01-15T10:30:00",
                          "status": 404,
                          "error": "Role Not Found",
                          "message": "Role not found with id: INVALID_ID"
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<RoleResponse> getRoleById(
        @Parameter(
            description = "Role ID (UUID)",
            example = "550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
        @PathVariable UUID id
    ) {
        RoleResponse response = roleHandler.getById(id);
        return ResponseEntity.ok(response);
    }
} 