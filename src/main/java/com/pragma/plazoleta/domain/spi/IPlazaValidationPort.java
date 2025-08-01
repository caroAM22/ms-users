package com.pragma.plazoleta.domain.spi;

import java.util.UUID;

public interface IPlazaValidationPort {
    boolean getRestaurantById(UUID restaurantId);
} 