package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.UsuarioRequestDto;
import com.pragma.plazacomida.application.dto.response.UsuarioResponseDto;
import com.pragma.plazacomida.application.handler.IUsuarioHandler;
import com.pragma.plazacomida.application.mapper.IUsuarioRequestMapper;
import com.pragma.plazacomida.application.mapper.IUsuarioResponseMapper;
import com.pragma.plazacomida.domain.api.IUsuarioServicePort;
import com.pragma.plazacomida.domain.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioHandler implements IUsuarioHandler {
    
    private final IUsuarioServicePort usuarioServicePort;
    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IUsuarioResponseMapper usuarioResponseMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UsuarioResponseDto saveUsuario(UsuarioRequestDto usuarioRequestDto) {
        // Verificar si el correo ya existe
        if (usuarioServicePort.existsByCorreo(usuarioRequestDto.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        // Verificar si el número de documento ya existe
        if (usuarioServicePort.existsByNumeroDocumento(usuarioRequestDto.getNumeroDocumento())) {
            throw new RuntimeException("El número de documento ya está registrado");
        }
        
        // Encriptar la contraseña
        UsuarioModel usuarioModel = usuarioRequestMapper.toUsuarioModel(usuarioRequestDto);
        usuarioModel.setClave(passwordEncoder.encode(usuarioRequestDto.getClave()));
        
        UsuarioModel savedUsuario = usuarioServicePort.saveUsuario(usuarioModel);
        return usuarioResponseMapper.toUsuarioResponseDto(savedUsuario);
    }
    
    @Override
    public List<UsuarioResponseDto> getAllUsuarios() {
        return usuarioServicePort.getAllUsuarios().stream()
                .map(usuarioResponseMapper::toUsuarioResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public UsuarioResponseDto getUsuarioById(Long id) {
        return usuarioServicePort.getUsuarioById(id)
                .map(usuarioResponseMapper::toUsuarioResponseDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    @Override
    public UsuarioResponseDto getUsuarioByCorreo(String correo) {
        return usuarioServicePort.getUsuarioByCorreo(correo)
                .map(usuarioResponseMapper::toUsuarioResponseDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    @Override
    public UsuarioResponseDto getUsuarioByNumeroDocumento(String numeroDocumento) {
        return usuarioServicePort.getUsuarioByNumeroDocumento(numeroDocumento)
                .map(usuarioResponseMapper::toUsuarioResponseDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    @Override
    public void deleteUsuarioById(Long id) {
        usuarioServicePort.deleteUsuarioById(id);
    }
} 