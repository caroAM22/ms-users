package com.pragma.plazacomida.application.handler;

import com.pragma.plazacomida.application.dto.request.UsuarioRequestDto;
import com.pragma.plazacomida.application.dto.response.UsuarioResponseDto;

import java.util.List;

public interface IUsuarioHandler {
    UsuarioResponseDto saveUsuario(UsuarioRequestDto usuarioRequestDto);
    List<UsuarioResponseDto> getAllUsuarios();
    UsuarioResponseDto getUsuarioById(Long id);
    UsuarioResponseDto getUsuarioByCorreo(String correo);
    UsuarioResponseDto getUsuarioByNumeroDocumento(String numeroDocumento);
    void deleteUsuarioById(Long id);
} 