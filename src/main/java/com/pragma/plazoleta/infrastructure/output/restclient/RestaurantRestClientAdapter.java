package com.pragma.plazoleta.infrastructure.output.restclient;

import com.pragma.plazoleta.domain.spi.IPlazaValidationPort;
import com.pragma.plazoleta.infrastructure.output.restclient.dto.RestaurantResponse;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantRestClientAdapter implements IPlazaValidationPort {

    private final PlazaFeignClient plazaFeignClient;

    @Override
    public boolean getRestaurantById(UUID restaurantId) {
        try {
            RestaurantResponse restaurantResponse = plazaFeignClient.getRestaurantById(restaurantId.toString());
            return restaurantResponse != null && restaurantResponse.getId() != null && !restaurantResponse.getId().isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }
} 