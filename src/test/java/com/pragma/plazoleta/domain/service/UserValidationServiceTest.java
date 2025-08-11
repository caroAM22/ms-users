package com.pragma.plazoleta.domain.service;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.IPlazaValidationPort;
import com.pragma.plazoleta.domain.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPlazaValidationPort plazaValidationPort;

    @InjectMocks
    private UserValidationService userValidationService;

    private User validUser;

    @BeforeEach
    void setUp() {
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
    void shouldReturnSuccessWhenUserIsValid() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenBirthDateIsNull() {
        validUser.setBirthDate(null);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Birth date is required", result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenUserIsUnder18() {
        LocalDate birthDate17Years = LocalDate.now().minusYears(17).plusDays(1);
        validUser.setBirthDate(birthDate17Years);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("User must be at least 18 years old", result.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenUserIsExactly18() {
        LocalDate birthDate18Years = LocalDate.now().minusYears(18);
        validUser.setBirthDate(birthDate18Years);
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenUserIsOlderThan18() {
        LocalDate birthDate25Years = LocalDate.now().minusYears(25);
        validUser.setBirthDate(birthDate25Years);
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "12345678901234"})
    void shouldReturnFailureWhenPhoneIsInvalid(String invalidPhone) {
        validUser.setPhone(invalidPhone);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        if (invalidPhone.isEmpty()) {
            assertEquals("Phone number is required", result.getMessage());
        } else {
            assertEquals("Phone number cannot exceed 13 characters", result.getMessage());
        }
    }

    @Test
    void shouldReturnFailureWhenPhoneIsNull() {
        validUser.setPhone(null);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Phone number is required", result.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1",
        "1234567890123",
        "+123456789123",
        "123456"
    })
    void shouldReturnSuccessWhenPhoneIsValid(String validPhone) {
        validUser.setPhone(validPhone);
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenEmailIsEmpty() {
        validUser.setEmail("");

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Email is required", result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenEmailIsNull() {
        validUser.setEmail(null);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Email is required", result.getMessage());
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
    void shouldReturnFailureWhenEmailHasInvalidFormat(String invalidEmail) {
        validUser.setEmail(invalidEmail);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Email must have a valid format: something@domain.com", result.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "valid-mail@gmail.com",
        "test@example.com",
        "user.name@domain.co.uk",
        "user+tag@example.org",
        "123@numbers.com"
    })
    void shouldReturnSuccessWhenEmailIsValid(String validEmail) {
        validUser.setEmail(validEmail);
        when(userPersistencePort.existsByEmail(validEmail)).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(false);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenEmailAlreadyExists() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(true);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Email already exists", result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenDocumentNumberAlreadyExists() {
        when(userPersistencePort.existsByEmail(validUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validUser.getDocumentNumber())).thenReturn(true);

        ValidationResult result = userValidationService.validateUser(validUser);

        assertFalse(result.isValid());
        assertEquals("Document number already exists", result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenOwnerCreatesEmployeeWithoutRestaurantId() {
        ValidationResult result = userValidationService.validateRestaurantForEmployee("OWNER", "EMPLOYEE", null);

        assertFalse(result.isValid());
        assertEquals("restaurantId is required for employees created by owner", result.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenOwnerCreatesEmployeeWithValidRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        when(plazaValidationPort.getRestaurantById(restaurantId)).thenReturn(true);

        ValidationResult result = userValidationService.validateRestaurantForEmployee("OWNER", "EMPLOYEE", restaurantId);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenOwnerCreatesEmployeeWithInvalidRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        when(plazaValidationPort.getRestaurantById(restaurantId)).thenReturn(false);

        ValidationResult result = userValidationService.validateRestaurantForEmployee("OWNER", "EMPLOYEE", restaurantId);

        assertFalse(result.isValid());
        assertEquals("Restaurant with id " + restaurantId + " does not exist", result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenAdminCreatesOwnerWithRestaurantId() {
        UUID restaurantId = UUID.randomUUID();

        ValidationResult result = userValidationService.validateRestaurantForEmployee("ADMIN", "OWNER", restaurantId);

        assertFalse(result.isValid());
        assertEquals("restaurantId is not allowed for OWNER created by ADMIN", result.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenAdminCreatesOwnerWithoutRestaurantId() {
        ValidationResult result = userValidationService.validateRestaurantForEmployee("ADMIN", "OWNER", null);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenCustomerIsCreatedWithoutRestaurantId() {
        ValidationResult result = userValidationService.validateRestaurantForEmployee("ADMIN", "CUSTOMER", null);

        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void shouldReturnFailureWhenCustomerIsCreatedWithRestaurantId() {
        UUID restaurantId = UUID.randomUUID();

        ValidationResult result = userValidationService.validateRestaurantForEmployee("ADMIN", "CUSTOMER", restaurantId);

        assertFalse(result.isValid());
        assertEquals("restaurantId is not allowed for CUSTOMER created by ADMIN", result.getMessage());
    }
} 