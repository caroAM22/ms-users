package com.pragma.plazoleta.domain.api;

import java.util.UUID;

public interface IRoleServicePort {
    UUID getRoleIdByName(String roleName);
} 