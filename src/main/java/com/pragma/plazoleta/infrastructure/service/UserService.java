package com.pragma.plazoleta.infrastructure.service;

import com.pragma.plazoleta.infrastructure.exception.AuthenticationException;
import com.pragma.plazoleta.infrastructure.output.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.output.repository.IUserRepository;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(AuthenticationException::new);
    }
    
    public String getRoleName(String roleId) {
        return roleRepository.findById(roleId)
            .map(r -> r.getName())
            .orElseThrow(AuthenticationException::new);
    }
} 