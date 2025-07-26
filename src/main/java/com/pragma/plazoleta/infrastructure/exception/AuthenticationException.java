package com.pragma.plazoleta.infrastructure.exception;

public class AuthenticationException extends RuntimeException {
    
    public AuthenticationException() {
        super("Invalid credentials");
    }
} 