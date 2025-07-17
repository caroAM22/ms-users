package com.pragma.plazacomida.domain.spi;

import com.pragma.plazacomida.domain.model.RolModel;

import java.util.List;
import java.util.Optional;

public interface IRolPersistencePort {
    RolModel saveRol(RolModel rolModel);
    List<RolModel> getAllRoles();
    Optional<RolModel> getRolById(Long id);
    Optional<RolModel> getRolByNombre(String nombre);
    void deleteRolById(Long id);
} 