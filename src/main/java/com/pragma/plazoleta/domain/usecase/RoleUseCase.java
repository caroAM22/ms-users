package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import com.pragma.plazoleta.application.dto.response.RoleResponse;

@Service
@RequiredArgsConstructor
public class RoleUseCase implements IRoleServicePort {
    
    private final IRolePersistencePort rolePersistencePort;
    
    @Override
    public UUID getRoleIdByName(String roleName) {
        return rolePersistencePort.findIdByName(roleName);
    }
    
    @Override
    public Role getRoleById(UUID roleId) {
        Role role = rolePersistencePort.findById(roleId);
        if (role == null) {
            throw new RoleNotFoundException(roleId.toString());
        }
        return role;
    }

    @Override
    public RoleResponse getById(UUID id) {
        Role role = rolePersistencePort.findById(id);
        if (role == null) {
            throw new RoleNotFoundException(id.toString());
        }
        return new RoleResponse(role.getId(), role.getName(), role.getDescription());
    }
} 