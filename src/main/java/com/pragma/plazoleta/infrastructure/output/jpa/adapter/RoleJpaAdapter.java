package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    
    @Override
    public Optional<UUID> findIdByName(String roleName) {
        return roleRepository.findIdByName(roleName).map(UUID::fromString);
    }
    
    @Override
    public Optional<Role> findRoleById(UUID roleId) {
        return roleRepository.findById(roleId.toString()).map(roleEntityMapper::toRole);
    }
} 