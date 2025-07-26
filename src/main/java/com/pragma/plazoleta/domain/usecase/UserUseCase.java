package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.exception.UserValidationException;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;
import com.pragma.plazoleta.domain.service.UserPermissionsService;

import static com.pragma.plazoleta.domain.utils.RegexPattern.EMAIL_PATTERN_REQUIRED;
import static com.pragma.plazoleta.domain.utils.RegexPattern.PHONE_PATTERN_REQUIRED;

@Service
@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;
    private final PasswordEncoder passwordEncoder;
    private final UserPermissionsService userPermissionsService;

    @Override
    public User createUser(User user, String creatorRoleName) {
        validateUser(user);
        String roleToAssign = userPermissionsService.getRoleToAssign(creatorRoleName);
        UUID roleId = roleServicePort.getRoleIdByName(roleToAssign);
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
    
    private void validateUser(User user) {
        validateAge(user.getBirthDate());
        validatePhone(user.getPhone());
        validateEmail(user.getEmail());
        validateEmailUniqueness(user.getEmail());
        validateDocumentUniqueness(user.getDocumentNumber());
    }
    
    private void validateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new UserValidationException("Birth date is required");
        }
        
        Period age = Period.between(birthDate, LocalDate.now());
        if (age.getYears() < MINIMUM_AGE_REQUIRED) {
            throw new UserValidationException("User must be at least 18 years old");
        }
    }
    
    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new UserValidationException("Phone number is required");
        }

        if (phone.length() > MAXIMUM_PHONE_LENGTH) {
            throw new UserValidationException("Phone number cannot exceed 13 characters");
        }
        
        if (!PHONE_PATTERN_REQUIRED.matcher(phone).matches()) {
            throw new UserValidationException("Phone number must contain only digits and optionally start with +");
        }
    }
    
    private void validateEmailUniqueness(String email) {
        if (userPersistencePort.existsByEmail(email)) {
            throw new UserValidationException("Email already exists");
        }
    }
    
    private void validateDocumentUniqueness(Long documentNumber) {
        if (userPersistencePort.existsByDocumentNumber(documentNumber)) {
            throw new UserValidationException("Document number already exists");
        }
    }
    
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new UserValidationException("Email is required");
        }
        
        if (!EMAIL_PATTERN_REQUIRED.matcher(email).matches()) {
            throw new UserValidationException("Email must have a valid format: something@domain.com");
        }
    }
} 