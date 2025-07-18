package com.pragma.plazacomida.infrastructure.output.repository;

import com.pragma.plazacomida.infrastructure.output.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"role"})
    Optional<UserEntity> findById(Long id);
    
    @EntityGraph(attributePaths = {"role"})
    Optional<UserEntity> findByEmail(String email);
    
    @EntityGraph(attributePaths = {"role"})
    Optional<UserEntity> findByDocumentNumber(String documentNumber);
    
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(String documentNumber);
} 