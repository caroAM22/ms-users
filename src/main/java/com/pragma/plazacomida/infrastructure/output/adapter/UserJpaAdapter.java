package com.pragma.plazacomida.infrastructure.output.adapter;

import com.pragma.plazacomida.domain.model.UserModel;
import com.pragma.plazacomida.domain.spi.IUserPersistencePort;
import com.pragma.plazacomida.infrastructure.output.entity.UserEntity;
import com.pragma.plazacomida.infrastructure.output.mapper.IUserEntityMapper;
import com.pragma.plazacomida.infrastructure.output.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    
    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toUserEntity(userModel);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityMapper.toUserModel(savedEntity);
    }
    
    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::toUserModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toUserModel);
    }
    
    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUserModel);
    }
    
    @Override
    public Optional<UserModel> getUserByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .map(userEntityMapper::toUserModel);
    }
    
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }
} 