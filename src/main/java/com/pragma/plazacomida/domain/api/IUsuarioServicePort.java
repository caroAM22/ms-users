package com.pragma.plazacomida.domain.api;

import com.pragma.plazacomida.domain.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServicePort {
    UsuarioModel saveUsuario(UsuarioModel usuarioModel);
    List<UsuarioModel> getAllUsuarios();
    Optional<UsuarioModel> getUsuarioById(Long id);
    Optional<UsuarioModel> getUsuarioByCorreo(String correo);
    Optional<UsuarioModel> getUsuarioByNumeroDocumento(String numeroDocumento);
    void deleteUsuarioById(Long id);
    boolean existsByCorreo(String correo);
    boolean existsByNumeroDocumento(String numeroDocumento);
} 