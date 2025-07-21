package com.pragma.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String lastname;
    private Long documentNumber;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private UUID roleId;
} 