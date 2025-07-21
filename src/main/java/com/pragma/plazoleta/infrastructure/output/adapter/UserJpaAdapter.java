package com.pragma.plazoleta.infrastructure.output.adapter;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.output.mapper.IUserEntityMapper;
import com.pragma.plazoleta.infrastructure.output.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(savedEntity);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByDocumentNumber(Long documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntityMapper::toUser)
                .toList();
    }
} 