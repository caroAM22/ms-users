package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserApi;
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
import java.util.regex.Pattern;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserUseCase implements IUserApi {
    
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;
    private final PasswordEncoder passwordEncoder;
    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?\\d{1,13}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?!.*\\.\\.)[a-zA-Z0-9]([a-zA-Z0-9._%+-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9.-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$");
    private static final String OWNER_ROLE_NAME = "OWNER";
    
    @Override
    public User createUser(User user) {
        validateUser(user);
        
        UUID ownerRoleId = roleServicePort.getRoleIdByName(OWNER_ROLE_NAME);
        
        User userToSave = new User();
        userToSave.setId(UUID.randomUUID());
        userToSave.setName(user.getName());
        userToSave.setLastname(user.getLastname());
        userToSave.setDocumentNumber(user.getDocumentNumber());
        userToSave.setPhone(user.getPhone());
        userToSave.setBirthDate(user.getBirthDate());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRoleId(ownerRoleId);
        
        return userPersistencePort.saveUser(userToSave);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }
    
    @Override
    public User getUserById(UUID userId) {
        User user = userPersistencePort.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return user;
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
        if (age.getYears() < 18) {
            throw new UserValidationException("User must be at least 18 years old");
        }
    }
    
    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new UserValidationException("Phone number is required");
        }
        
        if (phone.length() > 13) {
            throw new UserValidationException("Phone number cannot exceed 13 characters");
        }
        
        if (!PHONE_PATTERN.matcher(phone).matches()) {
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
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new UserValidationException("Email must have a valid format: something@domain.com");
        }
    }
} 