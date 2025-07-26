package com.pragma.plazoleta.infrastructure.output.repository;

import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
    
    boolean existsByEmail(String email);
    
    boolean existsByDocumentNumber(Long documentNumber);

    Optional<UserEntity> findByEmail(String email);
} 