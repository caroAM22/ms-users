package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.LoginRequestDto;
import com.pragma.plazacomida.application.dto.response.LoginResponseDto;
import com.pragma.plazacomida.application.handler.IAuthHandler;
import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthHandler implements IAuthHandler {
    
    private final IAuthServicePort authServicePort;
    
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Authenticate user
        UserModel user = authServicePort.authenticateUser(
                loginRequestDto.getEmail(), 
                loginRequestDto.getPassword()
        );
        
        // Generate tokens
        String accessToken = authServicePort.generateToken(user);
        String refreshToken = authServicePort.generateRefreshToken(user);
        
        return new LoginResponseDto(accessToken, refreshToken);
    }
} 