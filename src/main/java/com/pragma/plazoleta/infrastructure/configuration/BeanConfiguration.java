package com.pragma.plazoleta.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
import com.pragma.plazoleta.domain.service.UserValidationService;
import com.pragma.plazoleta.domain.spi.IUserValidationPort;
import com.pragma.plazoleta.domain.spi.IPlazaValidationPort;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.usecase.RoleUseCase;
import com.pragma.plazoleta.domain.usecase.UserUseCase;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.SecurityContextAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IUserRepository;
import com.pragma.plazoleta.infrastructure.output.restclient.PlazaFeignClient;
import com.pragma.plazoleta.infrastructure.output.restclient.RestaurantRestClientAdapter;
import com.pragma.plazoleta.infrastructure.security.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final PlazaFeignClient plazaFeignClient;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Bean
    public UserPermissionsService userPermissionsService() {
        return new UserPermissionsService();
    }

    @Bean
    public IUserValidationPort userValidationPort() {
        return new UserValidationService(userPersistencePort(), plazaValidationPort());
    }

    @Bean
    public IPlazaValidationPort plazaValidationPort() {
        return new RestaurantRestClientAdapter(plazaFeignClient);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public ISecurityContextPort securityContextPort() {
        return new SecurityContextAdapter(jwtService);
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(
            userPersistencePort(),
            roleServicePort(),
            passwordEncoder,
            userPermissionsService(),
            securityContextPort(),
            userValidationPort()
        );
    }
    
} 