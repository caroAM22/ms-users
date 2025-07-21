package com.pragma.plazoleta.domain.spi;

import java.util.UUID;
 
public interface IRolePersistencePort {
    UUID findIdByName(String roleName);
} 