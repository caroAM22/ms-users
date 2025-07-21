package com.pragma.plazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new user")
public class UserRequest {
    
    @Schema(description = "User's first name", example = "John")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Schema(description = "User's last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastname;
    
    @Schema(description = "User's document number", example = "1234567890")
    @NotNull(message = "Document number is required")
    @Min(value = 1000000, message = "Document number must be at least 7 digits")
    private Long documentNumber;
    
    @Schema(description = "User's phone number (max 13 characters, can start with +)", example = "+573001234567")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?\\d{1,13}$", message = "Phone number must contain only digits and optionally start with +")
    private String phone;
    
    @Schema(description = "User's birth date (must be 18 or older)", example = "1990-01-15")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must have a valid format: something@domain.com")
    private String email;
    
    @Schema(description = "User's password (will be encrypted)", example = "SecurePassword123!")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
} 