package com.pragma.plazoleta.infrastructure.exceptionhandler;

import com.pragma.plazoleta.application.dto.response.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ValidationErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ValidationErrorResponse error = ValidationErrorResponse.error(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        ValidationErrorResponse error = ValidationErrorResponse.error(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .collect(Collectors.joining(", "));
        
        ValidationErrorResponse error = ValidationErrorResponse.error(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
} 