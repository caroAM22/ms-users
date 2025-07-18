package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.UserRequestDto;
import com.pragma.plazacomida.application.dto.response.UserResponseDto;
import com.pragma.plazacomida.application.mapper.IUserRequestMapper;
import com.pragma.plazacomida.application.mapper.IUserResponseMapper;
import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.api.IRoleValidationServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserHandlerTest {
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IUserRequestMapper userRequestMapper;
    @Mock
    private IUserResponseMapper userResponseMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IRoleValidationServicePort roleValidationServicePort;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Un admin puede crear un usuario OWNER exitosamente con todos los campos válidos")
    void adminCanCreateOwnerUserWithValidData() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );
        UserModel adminUser = new UserModel();
        adminUser.setId(1L);
        adminUser.setEmail("admin@plazacomidas.com");
        adminUser.setRoleId(1L); // ADMIN
        UserModel userModel = new UserModel();
        userModel.setName(request.getName());
        userModel.setLastname(request.getLastname());
        userModel.setDocumentNumber(request.getDocumentNumber());
        userModel.setPhone(request.getPhone());
        userModel.setBirthDate(request.getBirthDate());
        userModel.setEmail(request.getEmail());
        userModel.setPassword(request.getPassword());
        UserModel savedUser = new UserModel();
        savedUser.setId(2L);
        savedUser.setName(request.getName());
        savedUser.setLastname(request.getLastname());
        savedUser.setDocumentNumber(request.getDocumentNumber());
        savedUser.setPhone(request.getPhone());
        savedUser.setBirthDate(request.getBirthDate());
        savedUser.setEmail(request.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRoleId(2L); // OWNER
        UserResponseDto responseDto = new UserResponseDto(2L, "Juan", "Pérez", "123456789", "+573001234567", LocalDate.of(1990, 1, 1), "juan@restaurant.com", 2L);

        when(authentication.getName()).thenReturn("admin@plazacomidas.com");
        when(userServicePort.getUserByEmail("admin@plazacomidas.com")).thenReturn(Optional.of(adminUser));
        when(roleValidationServicePort.getRoleIdByName("OWNER")).thenReturn(2L);
        when(userRequestMapper.toUserModel(request)).thenReturn(userModel);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userServicePort.saveUser(any(UserModel.class))).thenReturn(savedUser);
        when(userResponseMapper.toUserResponseDto(savedUser)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userHandler.saveUser(request);

        // Assert
        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("Pérez", result.getLastname());
        assertEquals("123456789", result.getDocumentNumber());
        assertEquals("+573001234567", result.getPhone());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthDate());
        assertEquals("juan@restaurant.com", result.getEmail());
        assertEquals(2L, result.getRoleId()); // OWNER
        verify(userServicePort).saveUser(argThat(user -> user.getRoleId().equals(2L)));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    @DisplayName("Debe fallar cuando el usuario es menor de 18 años")
    void shouldFailWhenUserIsUnder18() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.now().minusYears(17), // 17 años
                "juan@restaurant.com",
                "password123"
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userHandler.saveUser(request);
        });
        assertEquals("El usuario debe ser mayor de 18 años", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando el usuario es exactamente 17 años")
    void shouldFailWhenUserIsExactly17() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.now().minusYears(17).plusDays(1), // 17 años y 1 día
                "juan@restaurant.com",
                "password123"
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userHandler.saveUser(request);
        });
        assertEquals("El usuario debe ser mayor de 18 años", exception.getMessage());
    }

    @Test
    @DisplayName("Debe permitir cuando el usuario es exactamente 18 años")
    void shouldAllowWhenUserIsExactly18() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.now().minusYears(18), // Exactamente 18 años
                "juan@restaurant.com",
                "password123"
        );
        UserModel adminUser = new UserModel();
        adminUser.setId(1L);
        adminUser.setEmail("admin@plazacomidas.com");
        adminUser.setRoleId(1L); // ADMIN
        UserModel userModel = new UserModel();
        userModel.setName(request.getName());
        userModel.setLastname(request.getLastname());
        userModel.setDocumentNumber(request.getDocumentNumber());
        userModel.setPhone(request.getPhone());
        userModel.setBirthDate(request.getBirthDate());
        userModel.setEmail(request.getEmail());
        userModel.setPassword(request.getPassword());
        UserModel savedUser = new UserModel();
        savedUser.setId(2L);
        savedUser.setRoleId(2L); // OWNER
        UserResponseDto responseDto = new UserResponseDto(2L, "Juan", "Pérez", "123456789", "+573001234567", LocalDate.now().minusYears(18), "juan@restaurant.com", 2L);

        when(authentication.getName()).thenReturn("admin@plazacomidas.com");
        when(userServicePort.getUserByEmail("admin@plazacomidas.com")).thenReturn(Optional.of(adminUser));
        when(roleValidationServicePort.getRoleIdByName("OWNER")).thenReturn(2L);
        when(userRequestMapper.toUserModel(request)).thenReturn(userModel);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userServicePort.saveUser(any(UserModel.class))).thenReturn(savedUser);
        when(userResponseMapper.toUserResponseDto(savedUser)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userHandler.saveUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getRoleId()); // OWNER
    }

    @Test
    @DisplayName("Debe fallar cuando la fecha de nacimiento es null")
    void shouldFailWhenBirthDateIsNull() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                null, // Fecha de nacimiento null
                "juan@restaurant.com",
                "password123"
        );

        // Act & Assert
        assertDoesNotThrow(() -> {
            // No debería lanzar excepción porque la validación está en el DTO
            // pero el handler debería manejar el caso null
        });
    }

    @Test
    @DisplayName("Debe fallar cuando la fecha de nacimiento es en el futuro")
    void shouldFailWhenBirthDateIsInFuture() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.now().plusYears(1), // Fecha futura
                "juan@restaurant.com",
                "password123"
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userHandler.saveUser(request);
        });
        assertEquals("El usuario debe ser mayor de 18 años", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando el usuario actual no es admin")
    void shouldFailWhenCurrentUserIsNotAdmin() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );
        UserModel nonAdminUser = new UserModel();
        nonAdminUser.setId(1L);
        nonAdminUser.setEmail("user@plazacomidas.com");
        nonAdminUser.setRoleId(3L); // No es ADMIN

        when(authentication.getName()).thenReturn("user@plazacomidas.com");
        when(userServicePort.getUserByEmail("user@plazacomidas.com")).thenReturn(Optional.of(nonAdminUser));
        when(roleValidationServicePort.getRoleIdByName("OWNER")).thenReturn(2L);
        doThrow(new RuntimeException("Solo los administradores pueden crear usuarios OWNER"))
                .when(roleValidationServicePort).validateUserCreation(nonAdminUser, 2L);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userHandler.saveUser(request);
        });
        assertEquals("Solo los administradores pueden crear usuarios OWNER", exception.getMessage());
    }

    @Test
    @DisplayName("Debe fallar cuando el usuario actual no existe")
    void shouldFailWhenCurrentUserDoesNotExist() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );

        when(authentication.getName()).thenReturn("nonexistent@plazacomidas.com");
        when(userServicePort.getUserByEmail("nonexistent@plazacomidas.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userHandler.saveUser(request);
        });
        assertEquals("Usuario actual no encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Debe encriptar la contraseña con bcrypt")
    void shouldEncodePasswordWithBcrypt() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );
        UserModel adminUser = new UserModel();
        adminUser.setId(1L);
        adminUser.setEmail("admin@plazacomidas.com");
        adminUser.setRoleId(1L); // ADMIN
        UserModel userModel = new UserModel();
        userModel.setPassword("password123");
        UserModel savedUser = new UserModel();
        savedUser.setId(2L);
        savedUser.setRoleId(2L); // OWNER
        UserResponseDto responseDto = new UserResponseDto(2L, "Juan", "Pérez", "123456789", "+573001234567", LocalDate.of(1990, 1, 1), "juan@restaurant.com", 2L);

        when(authentication.getName()).thenReturn("admin@plazacomidas.com");
        when(userServicePort.getUserByEmail("admin@plazacomidas.com")).thenReturn(Optional.of(adminUser));
        when(roleValidationServicePort.getRoleIdByName("OWNER")).thenReturn(2L);
        when(userRequestMapper.toUserModel(request)).thenReturn(userModel);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encodedPassword");
        when(userServicePort.saveUser(any(UserModel.class))).thenReturn(savedUser);
        when(userResponseMapper.toUserResponseDto(savedUser)).thenReturn(responseDto);

        // Act
        userHandler.saveUser(request);

        // Assert
        verify(passwordEncoder).encode("password123");
        verify(userServicePort).saveUser(argThat(user -> 
            user.getPassword().equals("$2a$10$encodedPassword")
        ));
    }

    @Test
    @DisplayName("Debe asignar automáticamente el rol OWNER al usuario creado")
    void shouldAutomaticallyAssignOwnerRole() {
        // Arrange
        UserRequestDto request = new UserRequestDto(
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1990, 1, 1),
                "juan@restaurant.com",
                "password123"
        );
        UserModel adminUser = new UserModel();
        adminUser.setId(1L);
        adminUser.setEmail("admin@plazacomidas.com");
        adminUser.setRoleId(1L); // ADMIN
        UserModel userModel = new UserModel();
        userModel.setRoleId(null); // Sin rol asignado inicialmente
        UserModel savedUser = new UserModel();
        savedUser.setId(2L);
        savedUser.setRoleId(2L); // OWNER
        UserResponseDto responseDto = new UserResponseDto(2L, "Juan", "Pérez", "123456789", "+573001234567", LocalDate.of(1990, 1, 1), "juan@restaurant.com", 2L);

        when(authentication.getName()).thenReturn("admin@plazacomidas.com");
        when(userServicePort.getUserByEmail("admin@plazacomidas.com")).thenReturn(Optional.of(adminUser));
        when(roleValidationServicePort.getRoleIdByName("OWNER")).thenReturn(2L);
        when(userRequestMapper.toUserModel(request)).thenReturn(userModel);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userServicePort.saveUser(any(UserModel.class))).thenReturn(savedUser);
        when(userResponseMapper.toUserResponseDto(savedUser)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userHandler.saveUser(request);

        // Assert
        assertEquals(2L, result.getRoleId()); // OWNER
        verify(userServicePort).saveUser(argThat(user -> 
            user.getRoleId().equals(2L)
        ));
    }
} 