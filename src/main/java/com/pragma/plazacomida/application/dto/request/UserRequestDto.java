package com.pragma.plazacomida.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;
    
    @NotBlank(message = "Lastname is required")
    @Size(max = 50, message = "Lastname cannot exceed 50 characters")
    private String lastname;
    
    @NotBlank(message = "Document number is required")
    @Size(max = 20, message = "Document number cannot exceed 20 characters")
    private String documentNumber;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone must have between 10 and 15 digits")
    private String phone;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @NotBlank(message = "Email is required")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must have between 6 and 100 characters")
    private String password;
} 