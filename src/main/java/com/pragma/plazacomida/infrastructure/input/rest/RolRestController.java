package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.application.dto.request.RolRequestDto;
import com.pragma.plazacomida.application.dto.response.RolResponseDto;
import com.pragma.plazacomida.application.handler.IRolHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolRestController {
    
    private final IRolHandler rolHandler;
    
    @Operation(summary = "Crear un nuevo rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RolResponseDto> saveRol(@Valid @RequestBody RolRequestDto rolRequestDto) {
        return new ResponseEntity<>(rolHandler.saveRol(rolRequestDto), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Obtener todos los roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<RolResponseDto>> getAllRoles() {
        return ResponseEntity.ok(rolHandler.getAllRoles());
    }
    
    @Operation(summary = "Obtener un rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDto> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolHandler.getRolById(id));
    }
    
    @Operation(summary = "Obtener un rol por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolResponseDto> getRolByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(rolHandler.getRolByNombre(nombre));
    }
    
    @Operation(summary = "Eliminar un rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolById(@PathVariable Long id) {
        rolHandler.deleteRolById(id);
        return ResponseEntity.noContent().build();
    }
} 