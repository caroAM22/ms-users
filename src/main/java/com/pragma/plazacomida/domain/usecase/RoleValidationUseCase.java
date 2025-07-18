package com.pragma.plazacomida.domain.usecase;

import com.pragma.plazacomida.domain.api.IRoleValidationServicePort;
import com.pragma.plazacomida.domain.exception.UnauthorizedRoleException;
import com.pragma.plazacomida.domain.model.UserModel;
import com.pragma.plazacomida.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleValidationUseCase implements IRoleValidationServicePort {
    
    private final IRolePersistencePort rolePersistencePort;
    
    @Override
    public void validateUserCreation(UserModel currentUser, Long targetRoleId) {
        // Obtener ID del rol ADMIN
        Long adminRoleId = getRoleIdByName("ADMIN");
        
        // Validar que el usuario actual sea ADMIN
        if (!adminRoleId.equals(currentUser.getRoleId())) {
            throw new UnauthorizedRoleException("Solo los administradores pueden crear usuarios");
        }
        
        // Validar que el rol objetivo sea OWNER (por seguridad)
        Long ownerRoleId = getRoleIdByName("OWNER");
        if (!ownerRoleId.equals(targetRoleId)) {
            throw new UnauthorizedRoleException("Solo se pueden crear usuarios con rol OWNER");
        }
    }
    
    @Override
    public Long getRoleIdByName(String roleName) {
        return rolePersistencePort.findIdByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
    }
} 