package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;

public interface IRoleHandler {
    RoleResponse handle(RoleRequest request);
    RoleResponse getById(java.util.UUID id);
} 