package com.pragma.plazoleta.domain.exception;

public class RoleNotFoundException extends RuntimeException {
 
    public RoleNotFoundException() {
        super("Role not found");
    }
} 