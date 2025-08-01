package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.RoleEntity;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, String> {
    
    @Query("SELECT r.id FROM RoleEntity r WHERE r.name = :name")
    Optional<String> findIdByName(@Param("name") String name);
} 