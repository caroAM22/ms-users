package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
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
class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private RoleUseCase roleUseCase;

    @Test
    void shouldReturnRoleIdWhenRoleExists() {
        UUID adminRoleId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        String adminRoleName = "ADMIN";

        when(rolePersistencePort.findIdByName(adminRoleName))
                .thenReturn(adminRoleId);

        UUID result = roleUseCase.getRoleIdByName(adminRoleName);

        assertNotNull(result);
        assertEquals(adminRoleId, result);
    }
} 