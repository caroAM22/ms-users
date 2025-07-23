package com.pragma.plazoleta.infrastructure.exceptionhandler;

import com.pragma.plazoleta.domain.exception.ForbiddenOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ForbiddenExceptionHandler {

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenOperationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "You are not allowed to perform this action");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
} 