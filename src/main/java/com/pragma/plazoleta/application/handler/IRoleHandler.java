package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;
import java.util.UUID;

public interface IRoleHandler {
    RoleResponse handle(RoleRequest request);
    RoleResponse getById(UUID id);
} 