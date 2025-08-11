package com.pragma.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private boolean success;
    private String message;
    private String field;
    private String errorCode;
    
    public static ValidationErrorResponse error(String message) {
        return new ValidationErrorResponse(false, message, null, "VALIDATION_ERROR");
    }
    
    public static ValidationErrorResponse error(String message, String field) {
        return new ValidationErrorResponse(false, message, field, "VALIDATION_ERROR");
    }
    
    public static ValidationErrorResponse error(String message, String field, String errorCode) {
        return new ValidationErrorResponse(false, message, field, errorCode);
    }
} 