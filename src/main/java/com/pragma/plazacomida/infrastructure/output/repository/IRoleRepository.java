package com.pragma.plazacomida.infrastructure.output.repository;

import com.pragma.plazacomida.infrastructure.output.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    
    @Query("SELECT r.id FROM RoleEntity r WHERE r.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);
} 