package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.infrastructure.output.entity.RoleEntity;
import com.pragma.plazacomida.infrastructure.output.repository.IRoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleRestController {
    private final IRoleRepository roleRepository;

    @Operation(summary = "Get role name by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role name found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{id}/name")
    public ResponseEntity<String> getRoleNameById(@PathVariable Long id) {
        return roleRepository.findById(id)
                .map(role -> ResponseEntity.ok(role.getName()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get role description by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role description found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{id}/description")
    public ResponseEntity<String> getRoleDescriptionById(@PathVariable Long id) {
        return roleRepository.findById(id)
                .map(role -> ResponseEntity.ok(role.getDescription()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role list retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<java.util.List<RoleEntity>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
} 