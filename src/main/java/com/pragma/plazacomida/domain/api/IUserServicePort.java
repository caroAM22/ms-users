package com.pragma.plazacomida.domain.api;

import com.pragma.plazacomida.domain.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {
    UserModel saveUser(UserModel userModel);
    List<UserModel> getAllUsers();
    Optional<UserModel> getUserById(Long id);
    Optional<UserModel> getUserByEmail(String email);
    Optional<UserModel> getUserByDocumentNumber(String documentNumber);
    void deleteUserById(Long id);
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(String documentNumber);
} 