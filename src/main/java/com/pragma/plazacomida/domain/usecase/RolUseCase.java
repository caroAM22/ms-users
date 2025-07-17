package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IRolServicePort;
import com.pragma.plazacomida.domain.model.RolModel;
import com.pragma.plazacomida.domain.spi.IRolPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RolUseCase implements IRolServicePort {
    
    private final IRolPersistencePort rolPersistencePort;
    
    @Override
    public RolModel saveRol(RolModel rolModel) {
        return rolPersistencePort.saveRol(rolModel);
    }
    
    @Override
    public List<RolModel> getAllRoles() {
        return rolPersistencePort.getAllRoles();
    }
    
    @Override
    public Optional<RolModel> getRolById(Long id) {
        return rolPersistencePort.getRolById(id);
    }
    
    @Override
    public Optional<RolModel> getRolByNombre(String nombre) {
        return rolPersistencePort.getRolByNombre(nombre);
    }
    
    @Override
    public void deleteRolById(Long id) {
        rolPersistencePort.deleteRolById(id);
    }
} 