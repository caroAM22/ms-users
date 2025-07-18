package com.pragma.plazacomida.domain.exception;

public class UnauthorizedRoleException extends RuntimeException {
    public UnauthorizedRoleException(String message) {
        super(message);
    }
} 