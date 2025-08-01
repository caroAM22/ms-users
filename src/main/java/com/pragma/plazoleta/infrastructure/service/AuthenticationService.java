package com.pragma.plazoleta.infrastructure.service;

import com.pragma.plazoleta.infrastructure.exception.InvalidTokenException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public Map<String, String> loginWithTokens(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserEntity userEntity = userService.getUserByEmail(email);
        String roleName = userService.getRoleName(userEntity.getRoleId());
        
        String accessToken = jwtService.generateAccessToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail()
        );
        String refreshToken = jwtService.generateRefreshToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail()
        );
        return Map.of(
            "token", accessToken,
            "refreshToken", refreshToken
        );
    }

    public String refresh(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }
        String username = jwtService.extractUsername(refreshToken);
        UserEntity userEntity = userService.getUserByEmail(username);
        String roleName = userService.getRoleName(userEntity.getRoleId());
        
        return jwtService.generateAccessToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail()
        );
    }
} 