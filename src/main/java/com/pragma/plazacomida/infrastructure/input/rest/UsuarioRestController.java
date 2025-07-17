package com.pragma.plazacomida.infrastructure.input.rest;

import com.pragma.plazacomida.application.dto.request.UsuarioRequestDto;
import com.pragma.plazacomida.application.dto.response.UsuarioResponseDto;
import com.pragma.plazacomida.application.handler.IUsuarioHandler;
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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {
    
    private final IUsuarioHandler usuarioHandler;
    
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Usuario ya existe", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> saveUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        return new ResponseEntity<>(usuarioHandler.saveUsuario(usuarioRequestDto), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioHandler.getAllUsuarios());
    }
    
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioHandler.getUsuarioById(id));
    }
    
    @Operation(summary = "Obtener un usuario por correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioByCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioHandler.getUsuarioByCorreo(correo));
    }
    
    @Operation(summary = "Obtener un usuario por número de documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioByNumeroDocumento(@PathVariable String numeroDocumento) {
        return ResponseEntity.ok(usuarioHandler.getUsuarioByNumeroDocumento(numeroDocumento));
    }
    
    @Operation(summary = "Eliminar un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
        usuarioHandler.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }
} 