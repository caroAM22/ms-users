package com.pragma.plazacomida.application.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoValidationTest {
    
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("DTO válido debe pasar todas las validaciones")
    void validDtoShouldPassAllValidations() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Nombre es obligatorio")
    void nameIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                null, // Nombre null
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("name") && 
            v.getMessage().equals("Name is required")
        ));
    }

    @Test
    @DisplayName("Nombre no puede estar vacío")
    void nameCannotBeEmpty() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "", // Nombre vacío
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("name") && 
            v.getMessage().equals("Name is required")
        ));
    }

    @Test
    @DisplayName("Nombre no puede exceder 50 caracteres")
    void nameCannotExceed50Characters() {
        // Arrange
        String longName = "A".repeat(51); // 51 caracteres
        UserRequestDto dto = new UserRequestDto(
                longName,
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("name") && 
            v.getMessage().equals("Name cannot exceed 50 characters")
        ));
    }

    @Test
    @DisplayName("Apellido es obligatorio")
    void lastnameIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                null, // Apellido null
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("lastname") && 
            v.getMessage().equals("Lastname is required")
        ));
    }

    @Test
    @DisplayName("Documento de identidad es obligatorio")
    void documentNumberIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                null, // Documento null
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("documentNumber") && 
            v.getMessage().equals("Document number is required")
        ));
    }

    @Test
    @DisplayName("Documento de identidad no puede exceder 20 caracteres")
    void documentNumberCannotExceed20Characters() {
        // Arrange
        String longDocument = "1".repeat(21); // 21 caracteres
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                longDocument,
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("documentNumber") && 
            v.getMessage().equals("Document number cannot exceed 20 characters")
        ));
    }

    @Test
    @DisplayName("Teléfono es obligatorio")
    void phoneIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                null, // Teléfono null
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("phone") && 
            v.getMessage().equals("Phone is required")
        ));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+573001234567", // Formato válido con +
            "573001234567",  // Formato válido sin +
            "3001234567",    // Formato válido local
            "+57300123456",  // 11 dígitos
            "573001234567890" // 15 dígitos máximo
    })
    @DisplayName("Formatos de teléfono válidos")
    void validPhoneFormats(String phone) {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                phone,
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> 
            v.getPropertyPath().toString().equals("phone")
        ), "El teléfono " + phone + " debería ser válido");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "300123456",      // Muy corto (9 dígitos)
            "+5730012345678901", // Muy largo (16 dígitos)
            "abc123456789",   // Contiene letras
            "+57-300-123-4567", // Contiene guiones
            "+57 300 123 4567"  // Contiene espacios
    })
    @DisplayName("Formatos de teléfono inválidos")
    void invalidPhoneFormats(String phone) {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                phone,
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("phone") && 
            v.getMessage().equals("Phone must have between 10 and 15 digits")
        ), "El teléfono " + phone + " debería ser inválido");
    }

    @Test
    @DisplayName("Fecha de nacimiento es obligatoria")
    void birthDateIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                null, // Fecha null
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("birthDate") && 
            v.getMessage().equals("Birth date is required")
        ));
    }

    @Test
    @DisplayName("Fecha de nacimiento debe ser en el pasado")
    void birthDateMustBeInPast() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.now().plusDays(1), // Fecha futura
                "juan@restaurant.com",
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("birthDate") && 
            v.getMessage().equals("Birth date must be in the past")
        ));
    }

    @Test
    @DisplayName("Email es obligatorio")
    void emailIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                null, // Email null
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("email") && 
            v.getMessage().equals("Email is required")
        ));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "juan@restaurant.com",
            "juan.perez@restaurant.com",
            "juan_perez@restaurant.com",
            "juan+test@restaurant.com",
            "juan@restaurant.co",
            "juan@restaurant.org",
            "juan@restaurant.net"
    })
    @DisplayName("Formatos de email válidos")
    void validEmailFormats(String email) {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                email,
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> 
            v.getPropertyPath().toString().equals("email")
        ), "El email " + email + " debería ser válido");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "juan@",                    // Sin dominio
            "@restaurant.com",          // Sin usuario
            "juan.restaurant.com",      // Sin @
            "juan@restaurant"           // Sin extensión
    })
    @DisplayName("Formatos de email inválidos")
    void invalidEmailFormats(String email) {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                email,
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("email") && 
            v.getMessage().equals("Invalid email format")
        ), "El email " + email + " debería ser inválido");
    }

    @Test
    @DisplayName("Email no puede exceder 100 caracteres")
    void emailCannotExceed100Characters() {
        // Arrange
        String longEmail = "a".repeat(90) + "@restaurant.com"; // Más de 100 caracteres
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                longEmail,
                "password123"
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("email") && 
            v.getMessage().equals("Email cannot exceed 100 characters")
        ));
    }

    @Test
    @DisplayName("Contraseña es obligatoria")
    void passwordIsRequired() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                null // Contraseña null
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("password") && 
            v.getMessage().equals("Password is required")
        ));
    }

    @Test
    @DisplayName("Contraseña debe tener mínimo 6 caracteres")
    void passwordMustHaveMinimum6Characters() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "12345" // 5 caracteres
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("password") && 
            v.getMessage().equals("Password must have between 6 and 100 characters")
        ));
    }

    @Test
    @DisplayName("Contraseña no puede exceder 100 caracteres")
    void passwordCannotExceed100Characters() {
        // Arrange
        String longPassword = "a".repeat(101); // 101 caracteres
        UserRequestDto dto = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                longPassword
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> 
            v.getPropertyPath().toString().equals("password") && 
            v.getMessage().equals("Password must have between 6 and 100 characters")
        ));
    }

    @Test
    @DisplayName("Múltiples validaciones fallan simultáneamente")
    void multipleValidationsFailSimultaneously() {
        // Arrange
        UserRequestDto dto = new UserRequestDto(
                null, // Nombre faltante
                "",   // Apellido vacío
                "123456789",
                "invalid-phone", // Teléfono inválido
                LocalDate.now().plusDays(1), // Fecha futura
                "invalid-email", // Email inválido
                "123" // Contraseña muy corta
        );

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        // Assert
        assertEquals(6, violations.size(), "Deberían haber 6 violaciones de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastname")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDate")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
} 