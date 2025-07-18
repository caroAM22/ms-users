package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import com.pragma.plazacomida.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    
    private final IUserPersistencePort userPersistencePort;
    
    @Override
    public UserModel saveUser(UserModel userModel) {
        return userPersistencePort.saveUser(userModel);
    }
    
    @Override
    public List<UserModel> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }
    
    @Override
    public Optional<UserModel> getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }
    
    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userPersistencePort.getUserByEmail(email);
    }
    
    @Override
    public Optional<UserModel> getUserByDocumentNumber(String documentNumber) {
        return userPersistencePort.getUserByDocumentNumber(documentNumber);
    }
    
    @Override
    public void deleteUserById(Long id) {
        userPersistencePort.deleteUserById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userPersistencePort.existsByEmail(email);
    }
    
    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userPersistencePort.existsByDocumentNumber(documentNumber);
    }
} 