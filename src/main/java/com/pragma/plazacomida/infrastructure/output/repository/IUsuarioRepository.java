package com.pragma.plazacomida.infrastructure.output.repository;

import com.pragma.plazacomida.infrastructure.output.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByNumeroDocumento(String numeroDocumento);
    boolean existsByCorreo(String correo);
    boolean existsByNumeroDocumento(String numeroDocumento);
} 