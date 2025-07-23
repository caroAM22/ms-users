package com.pragma.plazoleta.domain.service;

import com.pragma.plazoleta.domain.exception.ForbiddenOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPermissionsServiceTest {
    private final UserPermissionsService service = new UserPermissionsService();

    @Test
    void adminCanCreateOwner() {
        assertEquals("OWNER", service.getRoleToAssign("ADMIN"));
    }

    @Test
    void ownerCanCreateEmployee() {
        assertEquals("EMPLOYEE", service.getRoleToAssign("OWNER"));
    }

    @Test
    void employeeCannotCreateUser() {
        assertThrows(ForbiddenOperationException.class, () ->
            service.getRoleToAssign("EMPLOYEE")
        );
    }
} 