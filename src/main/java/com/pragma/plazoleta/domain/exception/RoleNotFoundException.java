package com.pragma.plazoleta.domain.exception;

public class RoleNotFoundException extends RuntimeException {
 
    public RoleNotFoundException(String roleId) {
        super("Role not found with id: " + roleId);
    }
} 