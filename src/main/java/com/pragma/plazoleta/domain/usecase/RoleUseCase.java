package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import com.pragma.plazoleta.domain.exception.RoleNotFoundException;

@RequiredArgsConstructor
public class RoleUseCase implements IRoleServicePort {
    
    private final IRolePersistencePort rolePersistencePort;
    
    @Override
    public UUID getRoleIdByName(String roleName) {
        return rolePersistencePort.findIdByName(roleName)
            .orElseThrow(RoleNotFoundException::new);
    }
    
    @Override
    public Role getRoleById(UUID roleId) {
        return rolePersistencePort.findRoleById(roleId)
            .orElseThrow(RoleNotFoundException::new);
    }
} 