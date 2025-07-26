package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.application.mapper.IRoleMapper;
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
    private final IRoleMapper roleMapper;
    
    @Override
    public RoleResponse getRoleByName(String roleName) {
        UUID roleId = roleServicePort.getRoleIdByName(roleName);
        Role role = roleServicePort.getRoleById(roleId);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse getById(UUID id) {
        Role role = roleServicePort.getRoleById(id);
        return roleMapper.toRoleResponse(role);
    }
} 