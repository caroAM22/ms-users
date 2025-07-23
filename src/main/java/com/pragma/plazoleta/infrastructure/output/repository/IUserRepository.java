package com.pragma.plazoleta.infrastructure.output.repository;

import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {
    
    boolean existsByEmail(String email);
    
    boolean existsByDocumentNumber(Long documentNumber);

    java.util.Optional<UserEntity> findByEmail(String email);
} 