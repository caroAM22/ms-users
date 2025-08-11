package com.pragma.plazoleta.domain.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import com.pragma.plazoleta.domain.utils.Constants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private UUID id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname;
    
    @NotNull(message = "Document number is required")
    @Positive(message = "Document number must be positive")
    private Long documentNumber;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = Constants.PHONE_REGEX, 
             message = "Phone must be valid")
    private String phone;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @NotBlank(message = "Email is required")
    @Pattern(regexp = Constants.EMAIL_REGEX, 
             message = "Email must have a valid format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private UUID roleId;
    private UUID restaurantId;
} 