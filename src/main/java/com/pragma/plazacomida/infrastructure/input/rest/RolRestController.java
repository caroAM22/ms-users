package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.infrastructure.output.entity.RolEntity;
import com.pragma.plazacomida.infrastructure.output.repository.IRolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolRestController {
    private final IRolRepository rolRepository;

    @Operation(summary = "Obtener el nombre del rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre del rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}/nombre")
    public ResponseEntity<String> getNombreRolById(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(rol -> ResponseEntity.ok(rol.getNombre()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener la descripción del rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descripción del rol encontrada"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}/descripcion")
    public ResponseEntity<String> getDescripcionRolById(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(rol -> ResponseEntity.ok(rol.getDescripcion()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<java.util.List<RolEntity>> getAllRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }
} 