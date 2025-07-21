package com.pragma.plazoleta.infrastructure.output.adapter;

import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.infrastructure.output.entity.RoleEntity;
import com.pragma.plazoleta.infrastructure.output.mapper.IRoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    
    @Override
    public UUID findIdByName(String roleName) {
        return roleRepository.findIdByName(roleName)
                .map(UUID::fromString)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }
    
    @Override
    public Role findById(UUID roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId.toString())
                .orElseThrow(() -> new RoleNotFoundException(roleId.toString()));
        return roleEntityMapper.toRole(roleEntity);
    }
} 