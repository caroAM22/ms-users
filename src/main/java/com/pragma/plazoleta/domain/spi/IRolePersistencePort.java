package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Role;

import java.util.Optional;
import java.util.UUID;
 
public interface IRolePersistencePort {
    Optional<UUID> findIdByName(String roleName);
    Optional<Role> findRoleById(UUID roleId);
} 