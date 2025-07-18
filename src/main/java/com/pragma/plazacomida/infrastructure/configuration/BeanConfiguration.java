package com.pragma.plazacomida.infrastructure.configuration;

import com.pragma.plazacomida.domain.api.IAuthServicePort;
import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.api.IObjectServicePort;
import com.pragma.plazacomida.domain.spi.IUserPersistencePort;
import com.pragma.plazacomida.domain.spi.IObjectPersistencePort;
import com.pragma.plazacomida.domain.usecase.AuthUseCase;
import com.pragma.plazacomida.domain.usecase.UserUseCase;
import com.pragma.plazacomida.domain.usecase.ObjectUseCase;
import com.pragma.plazacomida.infrastructure.output.adapter.ObjectJpaAdapter;
import com.pragma.plazacomida.infrastructure.output.repository.IObjectRepository;
import com.pragma.plazacomida.infrastructure.output.mapper.IObjectEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    
    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort) {
        return new UserUseCase(userPersistencePort);
    }
    
    @Bean
    public IAuthServicePort authServicePort(IUserServicePort userServicePort, 
                                          org.springframework.security.crypto.password.PasswordEncoder passwordEncoder,
                                          JwtService jwtService) {
        return new AuthUseCase(userServicePort, passwordEncoder, jwtService);
    }

    @Bean
    public IObjectPersistencePort objectPersistencePort(IObjectRepository objectRepository, IObjectEntityMapper objectEntityMapper) {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort(IObjectPersistencePort objectPersistencePort) {
        return new ObjectUseCase(objectPersistencePort);
    }
}