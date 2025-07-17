package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.api.IUsuarioServicePort;
import com.pragma.plazacomida.domain.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {
    
    private final IUsuarioServicePort usuarioServicePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    @Override
    public String generateToken(UsuarioModel usuario) {
        return jwtService.generateToken(usuario);
    }
    
    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
    
    @Override
    public String getCorreoFromToken(String token) {
        return jwtService.getCorreoFromToken(token);
    }
    
    @Override
    public UsuarioModel authenticateUser(String correo, String clave) {
        return usuarioServicePort.getUsuarioByCorreo(correo)
                .filter(usuario -> passwordEncoder.matches(clave, usuario.getClave()))
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
    
    // Clase interna para manejo de JWT
    @RequiredArgsConstructor
    public static class JwtService {
        private final String secret;
        private final long expiration;
        
        public String generateToken(UsuarioModel usuario) {
            // Implementación básica - en producción usar librería JWT completa
            return "token_" + usuario.getCorreo() + "_" + System.currentTimeMillis();
        }
        
        public boolean validateToken(String token) {
            // Implementación básica - en producción validar JWT completo
            return token != null && token.startsWith("token_");
        }
        
        public String getCorreoFromToken(String token) {
            // Implementación básica - en producción extraer de JWT
            if (token != null && token.startsWith("token_")) {
                String[] parts = token.split("_");
                if (parts.length >= 2) {
                    return parts[1];
                }
            }
            return null;
        }
    }
} 