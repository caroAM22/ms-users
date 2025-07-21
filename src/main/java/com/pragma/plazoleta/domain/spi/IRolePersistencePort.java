package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Role;
import java.util.UUID;
 
public interface IRolePersistencePort {
    UUID findIdByName(String roleName);
    Role findById(UUID roleId);
} 