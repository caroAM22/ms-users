package com.pragma.plazoleta.domain.service;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.IPlazaValidationPort;
import com.pragma.plazoleta.domain.spi.IUserValidationPort;
import com.pragma.plazoleta.domain.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import com.pragma.plazoleta.domain.utils.Constants;

import java.time.Period;
import java.util.UUID;

@RequiredArgsConstructor
public class UserValidationService implements IUserValidationPort {
    
    private final IUserPersistencePort userPersistencePort;
    private final IPlazaValidationPort plazaValidationPort;
    
    public ValidationResult validateUser(User user) {
        ValidationResult ageValidation = validateAge(user.getBirthDate());
        if (ageValidation.isInvalid()) {
            return ageValidation;
        }
        
        ValidationResult phoneValidation = validatePhone(user.getPhone());
        if (phoneValidation.isInvalid()) {
            return phoneValidation;
        }
        
        ValidationResult emailValidation = validateEmail(user.getEmail());
        if (emailValidation.isInvalid()) {
            return emailValidation;
        }
        
        ValidationResult emailUniquenessValidation = validateEmailUniqueness(user.getEmail());
        if (emailUniquenessValidation.isInvalid()) {
            return emailUniquenessValidation;
        }
        
        ValidationResult documentUniquenessValidation = validateDocumentUniqueness(user.getDocumentNumber());
        if (documentUniquenessValidation.isInvalid()) {
            return documentUniquenessValidation;
        }
        
        return ValidationResult.success();
    }
    
    public ValidationResult validateRestaurantForEmployee(String creatorRole, String roleToAssign, UUID restaurantId) {
        if ("OWNER".equals(creatorRole) && "EMPLOYEE".equals(roleToAssign)) {
            if (restaurantId == null) {
                return ValidationResult.failure("restaurantId is required for employees created by owner");
            }
            
            if (!plazaValidationPort.getRestaurantById(restaurantId)) {
                return ValidationResult.failure("Restaurant with id " + restaurantId + " does not exist");
            }
        } else if (restaurantId != null) {
            return ValidationResult.failure("restaurantId is not allowed for " + roleToAssign + " created by " + creatorRole);
        }
        
        return ValidationResult.success();
    }
    
    private ValidationResult validateAge(java.time.LocalDate birthDate) {
        if (birthDate == null) {
            return ValidationResult.failure("Birth date is required");
        }
        
        Period age = Period.between(birthDate, java.time.LocalDate.now());
        if (age.getYears() < Constants.MINIMUM_AGE_REQUIRED) {
            return ValidationResult.failure("User must be at least " + Constants.MINIMUM_AGE_REQUIRED + " years old");
        }
        
        return ValidationResult.success();
    }
    
    private ValidationResult validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return ValidationResult.failure("Phone number is required");
        }

        if (phone.length() > Constants.MAXIMUM_PHONE_LENGTH) {
            return ValidationResult.failure("Phone number cannot exceed " + Constants.MAXIMUM_PHONE_LENGTH + " characters");
        }
        
        if (!Constants.PHONE_PATTERN_REQUIRED.matcher(phone).matches()) {
            return ValidationResult.failure("Phone number must contain only digits and optionally start with +");
        }
        
        return ValidationResult.success();
    }
    
    private ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.failure("Email is required");
        }
        
        if (!Constants.EMAIL_PATTERN_REQUIRED.matcher(email).matches()) {
            return ValidationResult.failure("Email must have a valid format: something@domain.com");
        }
        
        return ValidationResult.success();
    }
    
    private ValidationResult validateEmailUniqueness(String email) {
        if (userPersistencePort.existsByEmail(email)) {
            return ValidationResult.failure("Email already exists");
        }
        return ValidationResult.success();
    }
    
    private ValidationResult validateDocumentUniqueness(Long documentNumber) {
        if (userPersistencePort.existsByDocumentNumber(documentNumber)) {
            return ValidationResult.failure("Document number already exists");
        }
        return ValidationResult.success();
    }
} 