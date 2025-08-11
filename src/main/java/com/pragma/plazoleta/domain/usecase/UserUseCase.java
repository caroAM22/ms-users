package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserValidationPort;
import com.pragma.plazoleta.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
import com.pragma.plazoleta.domain.validation.ValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;
    private final IPasswordEncoderPort passwordEncoder;
    private final UserPermissionsService userPermissionsService;
    private final ISecurityContextPort securityContextPort;
    private final IUserValidationPort userValidationPort;

    @Override
    public User createUser(User user) {
        ValidationResult validationResult = userValidationPort.validateUser(user);
        if (validationResult.isInvalid()) {
            throw new IllegalArgumentException(validationResult.getMessage());
        }
        
        String creatorRole = securityContextPort.getRoleOfUserAutenticated();
        String roleToAssign = userPermissionsService.getRoleToAssign(creatorRole);
        UUID roleId = roleServicePort.getRoleIdByName(roleToAssign);
        
        ValidationResult restaurantValidation = userValidationPort.validateRestaurantForEmployee(
            creatorRole, roleToAssign, user.getRestaurantId()
        );
        if (restaurantValidation.isInvalid()) {
            throw new IllegalArgumentException(restaurantValidation.getMessage());
        }
        
        User userToSave = User.builder()
            .id(UUID.randomUUID())
            .name(user.getName())
            .lastname(user.getLastname())
            .documentNumber(user.getDocumentNumber())
            .phone(user.getPhone())
            .birthDate(user.getBirthDate())
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .roleId(roleId)
            .restaurantId(user.getRestaurantId())
            .build();
        return userPersistencePort.saveUser(userToSave);
    }

    @Override
    public User registerUser(User user) {
        ValidationResult validationResult = userValidationPort.validateUser(user);
        if (validationResult.isInvalid()) {
            throw new IllegalArgumentException(validationResult.getMessage());
        }
        
        UUID customerRoleId = roleServicePort.getRoleIdByName("CUSTOMER");
        User userToSave = User.builder()
            .id(UUID.randomUUID())
            .name(user.getName())
            .lastname(user.getLastname())
            .documentNumber(user.getDocumentNumber())
            .phone(user.getPhone())
            .birthDate(user.getBirthDate())
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .roleId(customerRoleId)
            .build();
        return userPersistencePort.saveUser(userToSave);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public User getUserById(UUID userId) {
        return userPersistencePort.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
} 