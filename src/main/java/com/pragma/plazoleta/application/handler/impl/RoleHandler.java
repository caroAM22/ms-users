package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.application.handler.IRoleHandler;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleHandler implements IRoleHandler {
    
    private final IRoleServicePort getRoleIdByNameApi;
    
    @Override
    public RoleResponse handle(RoleRequest request) {
        UUID roleId = getRoleIdByNameApi.getRoleIdByName(request.getRoleName());
        return new RoleResponse(roleId);
    }
} 