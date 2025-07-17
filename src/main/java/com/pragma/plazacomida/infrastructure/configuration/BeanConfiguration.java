package com.pragma.plazacomida.infrastructure.configuration;

import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.api.IUsuarioServicePort;
import com.pragma.plazacomida.domain.usecase.AuthUseCase;
import com.pragma.plazacomida.domain.usecase.UsuarioUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    @Bean
    public IUsuarioServicePort usuarioServicePort(com.pragma.plazacomida.domain.spi.IUsuarioPersistencePort usuarioPersistencePort) {
        return new UsuarioUseCase(usuarioPersistencePort);
    }
    
    @Bean
    public IAuthServicePort authServicePort(IUsuarioServicePort usuarioServicePort, 
                                          org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        AuthUseCase.JwtService jwtService = new AuthUseCase.JwtService(jwtSecret, jwtExpiration);
        return new AuthUseCase(usuarioServicePort, passwordEncoder, jwtService);
    }
}