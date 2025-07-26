package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.exception.RoleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                .thenReturn(Optional.of(adminRoleId));
        UUID result = roleUseCase.getRoleIdByName(adminRoleName);

        assertNotNull(result);
        assertEquals(adminRoleId, result);
    }
    
    @Test
    void shouldReturnRoleWhenRoleIdExists() {
        UUID roleId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Role expectedRole = new Role(roleId, "OWNER", "Restaurant owner");

        when(rolePersistencePort.findRoleById(roleId))
                .thenReturn(Optional.of(expectedRole));
        Role result = roleUseCase.getRoleById(roleId);

        assertNotNull(result);
        assertEquals(expectedRole.getId(), result.getId());
        assertEquals(expectedRole.getName(), result.getName());
        assertEquals(expectedRole.getDescription(), result.getDescription());
    }

    @Test
    void shouldThrowRoleNotFoundExceptionWhenRoleDoesNotExist() {
        UUID roleId = UUID.randomUUID();
        when(rolePersistencePort.findRoleById(roleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> {
            roleUseCase.getRoleById(roleId);
        });
    }
} 