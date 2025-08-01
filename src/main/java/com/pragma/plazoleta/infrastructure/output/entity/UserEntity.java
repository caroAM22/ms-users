package com.pragma.plazoleta.infrastructure.output.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;
    
    @Column(name = "document_number", nullable = false, unique = true)
    private Long documentNumber;
    
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "role_id", nullable = false, columnDefinition = "CHAR(36)")
    private String roleId;
    
    @Column(name = "restaurant_id", columnDefinition = "CHAR(36)")
    private String restaurantId;
} 