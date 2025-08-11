package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;
import com.pragma.plazoleta.domain.exception.ForbiddenOperationException;
import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.ISecurityContextPort;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
import com.pragma.plazoleta.domain.spi.IUserValidationPort;
import com.pragma.plazoleta.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRoleServicePort roleServicePort;

    @Mock
    private IPasswordEncoderPort passwordEncoder;

    @Mock
    private UserPermissionsService userPermissionsService;

    @Mock
    private ISecurityContextPort securityContextPort;

    @Mock
    private IUserValidationPort userValidationPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;
    private UUID ownerRoleId;

    @BeforeEach
    void setUp() {
        ownerRoleId = UUID.fromString("660e8400-e29b-41d4-a716-446655440001");
        
        validUser = new User();
        validUser.setName("John");
        validUser.setLastname("Doe");
        validUser.setDocumentNumber(1234567890L);
        validUser.setPhone("+573001234567");
        validUser.setBirthDate(LocalDate.of(1990, 1, 15));
        validUser.setEmail("john.doe@example.com");
        validUser.setPassword("SecurePassword123!");

        Mockito.reset(userPersistencePort, roleServicePort, passwordEncoder, 
                      userPermissionsService, securityContextPort, userValidationPort);
    }

    private void setupValidUserMocks() {
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("ADMIN");
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userValidationPort.validateUser(validUser)).thenReturn(ValidationResult.success());
        when(userValidationPort.validateRestaurantForEmployee("ADMIN", "OWNER", null)).thenReturn(ValidationResult.success());
        when(userPersistencePort.saveUser(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private User createTestUser(String name, String lastname, String email) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setDocumentNumber(1234567890L);
        user.setPhone("1234567890");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setPassword("password123");
        user.setRoleId(UUID.randomUUID());
        return user;
    }

    @Test
    void shouldCreateSuccessfullyWhenValidUser() {
        setupValidUserMocks();

        User result = userUseCase.createUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getName(), result.getName());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(ownerRoleId, result.getRoleId());
        verify(userValidationPort).validateUser(validUser);
    }

    @Test
    void shouldThrowExceptionWhenUserValidationFails() {
        when(userValidationPort.validateUser(validUser))
            .thenReturn(ValidationResult.failure("Validation failed"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userUseCase.createUser(validUser));

        assertEquals("Validation failed", exception.getMessage());
        verify(userValidationPort).validateUser(validUser);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantValidationFails() {
        when(userValidationPort.validateUser(validUser)).thenReturn(ValidationResult.success());
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("OWNER");
        when(userPermissionsService.getRoleToAssign("OWNER")).thenReturn("EMPLOYEE");
        when(userValidationPort.validateRestaurantForEmployee("OWNER", "EMPLOYEE", null))
            .thenReturn(ValidationResult.failure("restaurantId is required for employees created by owner"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userUseCase.createUser(validUser));

        assertEquals("restaurantId is required for employees created by owner", exception.getMessage());
        verify(userValidationPort).validateRestaurantForEmployee("OWNER", "EMPLOYEE", null);
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        List<User> expectedUsers = Arrays.asList(
            createTestUser("John", "Doe", "john@example.com"),
            createTestUser("Jane", "Smith", "jane@example.com")
        );

        when(userPersistencePort.getAllUsers()).thenReturn(expectedUsers);

        List<User> result = userUseCase.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedUsers, result);
    }
    
    @Test
    void shouldGetUserByIdSuccessfully() {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        User expectedUser = createTestUser("John", "Doe", "john@example.com");
        expectedUser.setId(userId);

        when(userPersistencePort.findById(userId)).thenReturn(Optional.of(expectedUser));

        User result = userUseCase.getUserById(userId);

        assertNotNull(result);
        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getName(), result.getName());
        assertEquals(expectedUser.getEmail(), result.getEmail());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        when(userPersistencePort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {   
            userUseCase.getUserById(userId);
        });
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userPersistencePort.getAllUsers()).thenReturn(Collections.emptyList());

        List<User> actualUsers = userUseCase.getAllUsers();

        assertNotNull(actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(userPersistencePort).getAllUsers();
    }

    @Test
    void employeeCannotCreateUser() {
        when(securityContextPort.getRoleOfUserAutenticated()).thenReturn("EMPLOYEE");
        when(userPermissionsService.getRoleToAssign("EMPLOYEE")).thenThrow(new ForbiddenOperationException());
        when(userValidationPort.validateUser(validUser)).thenReturn(ValidationResult.success());
        
        assertThrows(ForbiddenOperationException.class, () ->
            userUseCase.createUser(validUser)
        );
        verify(userPermissionsService).getRoleToAssign("EMPLOYEE");
    }

    @Test
    void shouldRegisterUserSuccessfullyWithCustomerRole() {
        UUID customerRoleId = UUID.fromString("660e8400-e29b-41d4-a716-446655440003");
        when(roleServicePort.getRoleIdByName("CUSTOMER")).thenReturn(customerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userValidationPort.validateUser(validUser)).thenReturn(ValidationResult.success());
        when(userPersistencePort.saveUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.registerUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getName(), result.getName());
        assertEquals(customerRoleId, result.getRoleId());
        assertEquals("encodedPassword", result.getPassword());
        
        verify(roleServicePort).getRoleIdByName("CUSTOMER");
        verify(passwordEncoder).encode(validUser.getPassword());
        verify(userPersistencePort).saveUser(any(User.class));
        verify(userValidationPort).validateUser(validUser);
    }

    @Test
    void shouldThrowRoleNotFoundExceptionWhenCustomerRoleDoesNotExist() {
        when(roleServicePort.getRoleIdByName("CUSTOMER")).thenThrow(new RoleNotFoundException());
        when(userValidationPort.validateUser(validUser)).thenReturn(ValidationResult.success());

        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Role not found", exception.getMessage());
        verify(roleServicePort).getRoleIdByName("CUSTOMER");
    }

    @Test
    void shouldThrowExceptionWhenUserValidationFailsDuringRegistration() {
        when(userValidationPort.validateUser(validUser))
            .thenReturn(ValidationResult.failure("Email already exists"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Email already exists", exception.getMessage());
        verify(userValidationPort).validateUser(validUser);
    }
} 