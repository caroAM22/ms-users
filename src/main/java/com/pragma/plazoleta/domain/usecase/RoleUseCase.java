package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleUseCase implements IRoleServicePort {
    
    private final IRolePersistencePort rolePersistencePort;
    
    @Override
    public UUID getRoleIdByName(String roleName) {
        return rolePersistencePort.findIdByName(roleName);
    }
} 