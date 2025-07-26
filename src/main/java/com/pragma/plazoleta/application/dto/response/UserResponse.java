package com.pragma.plazoleta.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response after creating a user")
public class UserResponse {
    
    @Schema(description = "Unique identifier of the created user", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;
    
    @Schema(description = "User's first name", example = "John")
    private String name;
    
    @Schema(description = "User's last name", example = "Doe")
    private String lastname;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "User's phone number", example = "+573001234567")
    private String phone;
    
    @Schema(description = "User's birth date", example = "1990-01-15")
    private LocalDate birthDate;
    
    @Schema(description = "User's role ID", example = "660e8400-e29b-41d4-a716-446655440001")
    private String roleId;
} 