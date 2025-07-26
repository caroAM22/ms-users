package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.response.RoleResponse;
import java.util.UUID;

public interface IRoleHandler {
    RoleResponse getRoleByName(String roleName);
    RoleResponse getById(UUID id);
} 