package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import com.pragma.plazacomida.infrastructure.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {
    
    private final IUserServicePort userServicePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    @Override
    public String generateToken(UserModel user) {
        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
        return jwtService.generateToken(userDetails);
    }
    
    @Override
    public String generateRefreshToken(UserModel user) {
        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
        return jwtService.generateRefreshToken(userDetails);
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            String email = jwtService.extractUsername(token);
            UserModel user = userServicePort.getUserByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            UserDetails userDetails = User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().getName())
                    .build();
            
            return jwtService.isTokenValid(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getEmailFromToken(String token) {
        return jwtService.extractUsername(token);
    }
    
    @Override
    public UserModel authenticateUser(String email, String password) {
        return userServicePort.getUserByEmail(email)
                .filter(user -> {
                    boolean match = passwordEncoder.matches(password, user.getPassword());
                    return match;
                })
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
} 