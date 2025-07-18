package com.pragma.plazacomida.domain.api;

import com.pragma.plazacomida.domain.model.UserModel;

public interface IAuthServicePort {
    String generateToken(UserModel user);
    String generateRefreshToken(UserModel user);
    boolean validateToken(String token);
    String getEmailFromToken(String token);
    UserModel authenticateUser(String email, String password);
} 