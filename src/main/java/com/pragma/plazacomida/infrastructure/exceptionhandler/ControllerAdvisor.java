package com.pragma.plazacomida.infrastructure.exceptionhandler;

import com.pragma.plazacomida.domain.exception.UnauthorizedRoleException;
import com.pragma.plazacomida.domain.exception.DuplicateResourceException;
import com.pragma.plazacomida.infrastructure.exception.NoDataFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }
    
    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedRoleException(
            UnauthorizedRoleException unauthorizedRoleException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, unauthorizedRoleException.getMessage()));
    }
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResourceException(
            DuplicateResourceException duplicateResourceException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, duplicateResourceException.getMessage()));
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException) {
        String message = "Error de integridad de datos";
        
        // Verificar si es un error de email duplicado
        if (dataIntegrityViolationException.getMessage().contains("email")) {
            message = "El email ya está registrado en el sistema";
        }
        // Verificar si es un error de documento duplicado
        else if (dataIntegrityViolationException.getMessage().contains("document")) {
            message = "El número de documento ya está registrado en el sistema";
        }
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }
}