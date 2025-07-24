package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.application.handler.IRoleHandler;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import com.pragma.plazoleta.domain.model.Role;

@Service
@RequiredArgsConstructor
public class RoleHandler implements IRoleHandler {
    
    private final IRoleServicePort roleServicePort;
    
    @Override
    public RoleResponse handle(RoleRequest request) {
        UUID roleId = roleServicePort.getRoleIdByName(request.getRoleName());
        Role role = roleServicePort.getRoleById(roleId);
        return new RoleResponse(role.getId(), role.getName(), role.getDescription());
    }

    @Override
    public RoleResponse getById(UUID id) {
        Role role = roleServicePort.getRoleById(id);
        return new RoleResponse(role.getId(), role.getName(), role.getDescription());
    }
} 