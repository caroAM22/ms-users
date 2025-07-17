package com.pragma.plazacomida.domain.api;

import com.pragma.plazacomida.domain.model.UsuarioModel;

public interface IAuthServicePort {
    String generateToken(UsuarioModel usuario);
    boolean validateToken(String token);
    String getCorreoFromToken(String token);
    UsuarioModel authenticateUser(String correo, String clave);
} 