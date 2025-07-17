package com.pragma.plazacomida.infrastructure.output.repository;

import com.pragma.plazacomida.infrastructure.output.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
} 