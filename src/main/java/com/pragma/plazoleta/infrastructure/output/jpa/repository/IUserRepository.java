package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.UserEntity;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
    
    boolean existsByEmail(String email);
    
    boolean existsByDocumentNumber(Long documentNumber);

    Optional<UserEntity> findByEmail(String email);
} 