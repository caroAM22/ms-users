package com.pragma.plazoleta.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
import com.pragma.plazoleta.domain.spi.IPlazaValidationPort;
import com.pragma.plazoleta.infrastructure.output.restclient.PlazaFeignClient;
import com.pragma.plazoleta.infrastructure.output.restclient.RestaurantRestClientAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final PlazaFeignClient plazaFeignClient;

    @Bean
    public UserPermissionsService userPermissionsService() {
        return new UserPermissionsService();
    }

    @Bean
    public IPlazaValidationPort plazaValidationPort() {
        return new RestaurantRestClientAdapter(plazaFeignClient);
    }
    
} 