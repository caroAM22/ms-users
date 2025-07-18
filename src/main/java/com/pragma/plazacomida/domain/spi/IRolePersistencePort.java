package com.pragma.plazacomida.domain.spi;

import com.pragma.plazacomida.domain.model.RoleModel;

import java.util.Optional;

public interface IRolePersistencePort {
    Optional<RoleModel> findByName(String name);
    Optional<Long> findIdByName(String name);
} 