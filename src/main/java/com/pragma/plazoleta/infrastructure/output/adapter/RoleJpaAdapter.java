package com.pragma.plazoleta.infrastructure.output.adapter;

import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.infrastructure.output.mapper.IRoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.repository.IRoleRepository;
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