package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.LoginRequestDto;
import com.pragma.plazacomida.application.dto.response.LoginResponseDto;
import com.pragma.plazacomida.application.dto.response.UsuarioResponseDto;
import com.pragma.plazacomida.application.handler.IAuthHandler;
import com.pragma.plazacomida.application.mapper.IUsuarioResponseMapper;
import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthHandler implements IAuthHandler {
    
    private final IAuthServicePort authServicePort;
    private final IUsuarioResponseMapper usuarioResponseMapper;
    
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Autenticar usuario
        UsuarioModel usuario = authServicePort.authenticateUser(
                loginRequestDto.getCorreo(), 
                loginRequestDto.getClave()
        );
        
        // Generar token
        String token = authServicePort.generateToken(usuario);
        
        // Convertir a DTO de respuesta
        UsuarioResponseDto usuarioResponseDto = usuarioResponseMapper.toUsuarioResponseDto(usuario);
        
        return new LoginResponseDto(token, "Bearer", usuarioResponseDto);
    }
} 