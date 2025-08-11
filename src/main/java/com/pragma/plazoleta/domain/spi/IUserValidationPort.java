package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.validation.ValidationResult;

import java.util.UUID;

public interface IUserValidationPort {
    ValidationResult validateUser(User user);
    ValidationResult validateRestaurantForEmployee(String creatorRole, String roleToAssign, UUID restaurantId);
} 