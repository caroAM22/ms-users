package com.pragma.plazacomida.domain.api;

import com.pragma.plazacomida.domain.model.UserModel;

public interface IRoleValidationServicePort {
    /**
     * Valida si un usuario puede crear otro usuario con el rol especificado
     * @param currentUser Usuario que está realizando la operación
     * @param targetRoleId ID del rol que se quiere asignar al nuevo usuario
     * @throws UnauthorizedRoleException si no tiene permisos
     */
    void validateUserCreation(UserModel currentUser, Long targetRoleId);
    
    /**
     * Obtiene el ID del rol por nombre
     * @param roleName Nombre del rol
     * @return ID del rol
     * @throws RuntimeException si el rol no existe
     */
    Long getRoleIdByName(String roleName);
} 