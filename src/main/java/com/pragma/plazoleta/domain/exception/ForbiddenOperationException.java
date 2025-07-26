package com.pragma.plazoleta.domain.exception;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException() {
        super("Don't have permission");
    }
} 