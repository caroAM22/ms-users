package com.pragma.plazoleta.domain.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private boolean valid;
    private String message;
    
    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }
    
    public static ValidationResult failure(String message) {
        return new ValidationResult(false, message);
    }

    public boolean isInvalid() {
        return !valid;
    }
}



