package com.pragma.plazoleta.infrastructure.service;

import com.pragma.plazoleta.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.pragma.plazoleta.infrastructure.output.repository.IUserRepository;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import com.pragma.plazoleta.domain.exception.InvalidTokenException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public Map<String, String> loginWithTokens(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String roleName = roleRepository.findById(userEntity.getRoleId())
                .map(r -> r.getName())
                .orElse("USER");
        String accessToken = jwtService.generateToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail(),
            jwtService.getJwtExpirationMillis()
        );
        String refreshToken = jwtService.generateToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail(),
            jwtService.getJwtRefreshExpirationMillis()
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
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String roleName = roleRepository.findById(userEntity.getRoleId())
                .map(r -> r.getName())
                .orElse("USER");
        return jwtService.generateToken(
            userEntity.getId(),
            roleName,
            userEntity.getEmail(),
            jwtService.getJwtRefreshExpirationMillis()
        );
    }
} 