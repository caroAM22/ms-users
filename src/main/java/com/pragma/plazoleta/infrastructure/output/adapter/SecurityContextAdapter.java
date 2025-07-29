package com.pragma.plazoleta.infrastructure.output.adapter;

import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
import com.pragma.plazoleta.infrastructure.exception.InvalidTokenException;
import com.pragma.plazoleta.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityContextAdapter implements ISecurityContextPort {
    
    private final JwtService jwtService;

    @Override
    public String getRoleOfUserAutenticated() {
        String token = extractTokenFromRequest();
        return jwtService.extractRole(token);
    }

    @Override
    public UUID getUserIdOfUserAutenticated() {
        String token = extractTokenFromRequest();
        String userId = jwtService.extractUserId(token);
        return UUID.fromString(userId);
    }
    
    private String extractTokenFromRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new InvalidTokenException("No authentication found in token");
        }
        return (String) auth.getPrincipal();
    }
}
