package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.exception.UserValidationException;
import com.pragma.plazoleta.domain.exception.UserNotFoundException;
import com.pragma.plazoleta.domain.exception.ForbiddenOperationException;
import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserPermissionsService userPermissionsService;

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
    }

    @Test
    void shouldCreateUserSuccessfullyWhenValidUser() {
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenReturn(validUser);

        User result = userUseCase.createUser(validUser, "ADMIN");

        assertNotNull(result);
        assertEquals(validUser.getName(), result.getName());
        assertEquals(validUser.getPassword(), result.getPassword());
        assertEquals(validUser.getRoleId(), result.getRoleId());
    }

    @Test
    void shouldThrowExceptionWhenUserUnder18() {
        LocalDate birthDate17Years = LocalDate.now().minusYears(17).plusDays(1);
        validUser.setBirthDate(birthDate17Years);

        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));

        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenUserExactly18() {
        LocalDate birthDate18Years = LocalDate.now().minusYears(18);
        validUser.setBirthDate(birthDate18Years);
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenReturn(validUser);

        assertDoesNotThrow(() -> userUseCase.createUser(validUser, "ADMIN"));
    }

    @Test
    void shouldNotThrowExceptionWhenUserOlderThan18() {
        LocalDate birthDate25Years = LocalDate.now().minusYears(25);
        validUser.setBirthDate(birthDate25Years);
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenReturn(validUser);

        assertDoesNotThrow(() -> userUseCase.createUser(validUser, "ADMIN"));
    }

    @Test
    void shouldThrowExceptionWhenFutureBirthDate() {
        LocalDate futureBirthDate = LocalDate.now().plusYears(1);
        validUser.setBirthDate(futureBirthDate);

        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));

        assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "invalid-email",
        "invalid-mail@", 
        "invalid-mail@gmailcom",
        "test@",
        "@gmail.com",
        "test..test@gmail.com",
        "test@.com",
        "test@gmail.",
        "test@gmail..com",
        "test@test@test.com",
        "test@.test.com"
    })
    void shouldThrowExceptionWhenInvalidEmail(String invalidEmail) {
        validUser.setEmail(invalidEmail);

        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));

        assertEquals("Email must have a valid format: something@domain.com", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "valid-mail@gmail.com",
        "test@example.com",
        "user.name@domain.co.uk",
        "user+tag@example.org",
        "123@numbers.com"
    })
    void shouldNotThrowExceptionWhenValidEmail(String validEmail) {
        validUser.setEmail(validEmail);
        setupValidUserMocks();

        assertDoesNotThrow(() -> userUseCase.createUser(validUser, "ADMIN"));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        validUser.setEmail("");
        UserValidationException exception = assertThrows(UserValidationException.class,
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsNull() {
        validUser.setBirthDate(null);
        UserValidationException exception = assertThrows(UserValidationException.class,
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Birth date is required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPhoneExceedsLength() {
        validUser.setPhone("12345678901234"); 
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Phone number cannot exceed 13 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "123+456789",  
        "abc123def",       
        "123-456-7890",     
        "123abc456",      
        "phone123",      
        "123@456"        
    })
    void shouldThrowExceptionWhenPhoneHasInvalidFormat(String invalidPhone) {
        validUser.setPhone(invalidPhone);
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Phone number must contain only digits and optionally start with +", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsEmpty() {
        validUser.setPhone("");
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Phone number is required", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1",                
        "1234567890123",   
        "+123456789123",
        "123456"  
    })
    void shouldNotThrowExceptionWhenValidPhone(String validPhone) {
        validUser.setPhone(validPhone);
        setupValidUserMocks();

        assertDoesNotThrow(() -> userUseCase.createUser(validUser, "ADMIN"));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(true);
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDocumentExists() {
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(true);
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.createUser(validUser, "ADMIN"));
        assertEquals("Document number already exists", exception.getMessage());
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
    void adminCanCreateOwner() {
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createUser(validUser, "ADMIN");
        assertNotNull(result);
        verify(userPermissionsService).getRoleToAssign("ADMIN");
        assertEquals(ownerRoleId, result.getRoleId());
    }

    @Test
    void ownerCanCreateEmployee() {
        UUID employeeRoleId = UUID.randomUUID();
        when(userPermissionsService.getRoleToAssign("OWNER")).thenReturn("EMPLOYEE");
        when(roleServicePort.getRoleIdByName("EMPLOYEE")).thenReturn(employeeRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createUser(validUser, "OWNER");
        assertNotNull(result);
        verify(userPermissionsService).getRoleToAssign("OWNER");
        assertEquals(employeeRoleId, result.getRoleId());
    }

    @Test
    void employeeCannotCreateUser() {
        when(userPermissionsService.getRoleToAssign("EMPLOYEE")).thenThrow(new ForbiddenOperationException());
        assertThrows(ForbiddenOperationException.class, () ->
            userUseCase.createUser(validUser, "EMPLOYEE")
        );
        verify(userPermissionsService).getRoleToAssign("EMPLOYEE");
    }

    private void setupValidUserMocks() {
        when(roleServicePort.getRoleIdByName("OWNER")).thenReturn(ownerRoleId);
        when(userPermissionsService.getRoleToAssign("ADMIN")).thenReturn("OWNER");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
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
    void shouldRegisterUserSuccessfullyWithCustomerRole() {
        UUID customerRoleId = UUID.fromString("660e8400-e29b-41d4-a716-446655440003");
        when(roleServicePort.getRoleIdByName("CUSTOMER")).thenReturn(customerRoleId);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);
        when(userPersistencePort.saveUser(any())).thenAnswer(invocation -> invocation.getArgument(0));


        User result = userUseCase.registerUser(validUser);

        assertNotNull(result);
        assertEquals(validUser.getName(), result.getName());
        assertEquals(customerRoleId, result.getRoleId());
        assertEquals("encodedPassword", result.getPassword());
        
        verify(roleServicePort).getRoleIdByName("CUSTOMER");
        verify(passwordEncoder).encode(validUser.getPassword());
        verify(userPersistencePort).saveUser(any(User.class));
    }

    @Test
    void shouldThrowRoleNotFoundExceptionWhenCustomerRoleDoesNotExist() {
        when(roleServicePort.getRoleIdByName("CUSTOMER")).thenThrow(new RoleNotFoundException());

        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Role not found", exception.getMessage());
        verify(roleServicePort).getRoleIdByName("CUSTOMER");
    }

    @Test
    void shouldValidateUserDataWhenRegistering() {
        validUser.setEmail("invalid-email");
        
        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Email must have a valid format: something@domain.com", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailExistsDuringRegistration() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(true);

        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Email already exists", exception.getMessage());
        verify(userPersistencePort).existsByEmail(validUser.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenDocumentExistsDuringRegistration() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(true);

        UserValidationException exception = assertThrows(UserValidationException.class, 
            () -> userUseCase.registerUser(validUser));
        
        assertEquals("Document number already exists", exception.getMessage());
        verify(userPersistencePort).existsByEmail(validUser.getEmail());
        verify(userPersistencePort).existsByDocumentNumber(validUser.getDocumentNumber());
    }
} 