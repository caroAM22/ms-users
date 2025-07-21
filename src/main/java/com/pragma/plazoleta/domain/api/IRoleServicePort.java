package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Role;
import java.util.UUID;

public interface IRoleServicePort {
    UUID getRoleIdByName(String roleName);
    Role getRoleById(UUID roleId);
} 