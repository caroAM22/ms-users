package com.pragma.plazoleta.infrastructure.exceptionhandler;

import com.pragma.plazoleta.domain.exception.UserValidationException;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {
    
    private static final String MESSAGE_KEY = "message";
    
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Map<String, Object>> handleUserValidationException(UserValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_KEY, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_KEY, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        String fieldName = ex.getBindingResult().getFieldErrors().get(0).getField();
        String defaultMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        
        String message;
        if ("email".equals(fieldName)) {
            message = "Email must have a valid format: something@domain.com";
        } else {
            message = defaultMessage;
        }
        
        response.put(MESSAGE_KEY, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
} 