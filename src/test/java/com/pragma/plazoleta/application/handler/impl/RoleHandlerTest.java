package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RoleRequest;
import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleHandlerTest {

    @Mock
    private IRoleServicePort getRoleIdByNameApi;

    @InjectMocks
    private RoleHandler getRoleIdByNameHandler;

    @Test
    void shouldReturnAdminRoleIdResponseWhenAdminRoleRequest() {
        UUID adminRoleId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        String adminRoleName = "ADMIN";
        RoleRequest request = new RoleRequest(adminRoleName);
        
        when(getRoleIdByNameApi.getRoleIdByName(adminRoleName))
                .thenReturn(adminRoleId);

        RoleResponse response = getRoleIdByNameHandler.handle(request);

        assertNotNull(response);
        assertEquals(adminRoleId, response.getId());
    }


} 