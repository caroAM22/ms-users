package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserApi;
import com.pragma.plazoleta.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pragma.plazoleta.application.dto.response.UserRoleResponse;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.Role;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserApi userApi;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private IRoleServicePort roleServicePort;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequest userRequest;
    private User domainUser;
    private UserResponse expectedResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setLastname("Doe");
        userRequest.setDocumentNumber(1234567890L);
        userRequest.setPhone("+573001234567");
        userRequest.setBirthDate(LocalDate.of(1990, 1, 15));
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("SecurePassword123!");

        domainUser = new User();
        domainUser.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        domainUser.setName("John");
        domainUser.setLastname("Doe");
        domainUser.setDocumentNumber(1234567890L);
        domainUser.setPhone("+573001234567");
        domainUser.setBirthDate(LocalDate.of(1990, 1, 15));
        domainUser.setEmail("john.doe@example.com");
        domainUser.setPassword("encodedPassword");
        domainUser.setRoleId(UUID.fromString("660e8400-e29b-41d4-a716-446655440001"));

        expectedResponse = new UserResponse();
        expectedResponse.setId(domainUser.getId());
        expectedResponse.setName(domainUser.getName());
        expectedResponse.setLastname(domainUser.getLastname());
        expectedResponse.setEmail(domainUser.getEmail());
        expectedResponse.setPhone(domainUser.getPhone());
        expectedResponse.setBirthDate(domainUser.getBirthDate());
        expectedResponse.setRoleId(domainUser.getRoleId());
    }

    @Test
    void shouldReturnUserResponseWhenValidRequest() {
        when(userMapper.toUser(userRequest)).thenReturn(domainUser);
        when(userApi.createUser(domainUser)).thenReturn(domainUser);
        when(userMapper.toUserResponse(domainUser)).thenReturn(expectedResponse);

        UserResponse result = userHandler.handle(userRequest);

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        assertEquals(expectedResponse.getEmail(), result.getEmail());
        assertEquals(expectedResponse.getRoleId(), result.getRoleId());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserRequest request = createTestUserRequest("John", "Doe", "john@example.com");
        User testUser = createTestUser("John", "Doe", "john@example.com");
        UserResponse testResponse = createTestUserResponse("John", "Doe", "john@example.com");

        when(userMapper.toUser(request)).thenReturn(testUser);
        when(userApi.createUser(testUser)).thenReturn(testUser);
        when(userMapper.toUserResponse(testUser)).thenReturn(testResponse);

        UserResponse actualResponse = userHandler.handle(request);

        assertNotNull(actualResponse);
        assertEquals(testResponse.getId(), actualResponse.getId());
        assertEquals(testResponse.getName(), actualResponse.getName());
        assertEquals(testResponse.getLastname(), actualResponse.getLastname());
        assertEquals(testResponse.getEmail(), actualResponse.getEmail());
        verify(userMapper).toUser(request);
        verify(userApi).createUser(testUser);
        verify(userMapper).toUserResponse(testUser);
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        List<User> domainUsers = Arrays.asList(
            createTestUser("John", "Doe", "john@example.com"),
            createTestUser("Jane", "Smith", "jane@example.com"),
            createTestUser("Bob", "Johnson", "bob@example.com")
        );

        List<UserResponse> expectedResponses = Arrays.asList(
            createTestUserResponse("John", "Doe", "john@example.com"),
            createTestUserResponse("Jane", "Smith", "jane@example.com"),
            createTestUserResponse("Bob", "Johnson", "bob@example.com")
        );

        when(userApi.getAllUsers()).thenReturn(domainUsers);
        for (int i = 0; i < domainUsers.size(); i++) {
            when(userMapper.toUserResponse(domainUsers.get(i))).thenReturn(expectedResponses.get(i));
        }

        List<UserResponse> actualResponses = userHandler.getAllUsers();

        assertNotNull(actualResponses);
        assertEquals(3, actualResponses.size());
        assertEquals(expectedResponses, actualResponses);
        verify(userApi).getAllUsers();
        for (User user : domainUsers) {
            verify(userMapper).toUserResponse(user);
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userApi.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserResponse> actualResponses = userHandler.getAllUsers();

        assertNotNull(actualResponses);
        assertTrue(actualResponses.isEmpty());
        verify(userApi).getAllUsers();
    }
    
    @Test
    void shouldGetUserRoleSuccessfully() {
        UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID roleId = UUID.fromString("660e8400-e29b-41d4-a716-446655440001");
        
        User testUser = createTestUser("John", "Doe", "john@example.com");
        testUser.setId(userId);
        testUser.setRoleId(roleId);
        
        Role testRole = new Role(roleId, "OWNER", "Restaurant owner");
        
        UserRoleResponse expectedResponseUserRole = new UserRoleResponse(roleId, "OWNER");

        when(userApi.getUserById(userId)).thenReturn(testUser);
        when(roleServicePort.getRoleById(roleId)).thenReturn(testRole);

        UserRoleResponse actualResponse = userHandler.getUserRole(userId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponseUserRole.getRoleId(), actualResponse.getRoleId());
        assertEquals(expectedResponseUserRole.getRoleName(), actualResponse.getRoleName());
        verify(userApi).getUserById(userId);
        verify(roleServicePort).getRoleById(roleId);
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

    private UserRequest createTestUserRequest(String name, String lastname, String email) {
        UserRequest request = new UserRequest();
        request.setName(name);
        request.setLastname(lastname);
        request.setDocumentNumber(1234567890L);
        request.setPhone("1234567890");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setEmail(email);
        request.setPassword("password123");
        return request;
    }

    private UserResponse createTestUserResponse(String name, String lastname, String email) {
        UserResponse response = new UserResponse();
        response.setId(UUID.randomUUID());
        response.setName(name);
        response.setLastname(lastname);
        response.setEmail(email);
        response.setPhone("1234567890");
        response.setBirthDate(LocalDate.of(1990, 1, 1));
        response.setRoleId(UUID.randomUUID());
        return response;
    }
} 