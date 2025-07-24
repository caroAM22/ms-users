package com.pragma.plazoleta.domain.service;

import com.pragma.plazoleta.domain.exception.ForbiddenOperationException;

public class UserPermissionsService {
    public String getRoleToAssign(String creatorRoleName) {
        if ("ADMIN".equals(creatorRoleName)) return "OWNER";
        if ("OWNER".equals(creatorRoleName)) return "EMPLOYEE";
        throw new ForbiddenOperationException("Don't have permission");
    }
} 