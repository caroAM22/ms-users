package com.pragma.plazoleta.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.pragma.plazoleta.domain.service.UserPermissionsService;

@Configuration
@EnableWebSecurity
public class BeanConfiguration {

    @Bean
    public UserPermissionsService userPermissionsService() {
        return new UserPermissionsService();
    }
    
} 