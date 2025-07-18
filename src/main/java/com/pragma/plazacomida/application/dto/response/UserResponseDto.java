package com.pragma.plazacomida.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private Long roleId;
} 