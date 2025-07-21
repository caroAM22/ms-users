package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.dto.response.UserRoleResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserApi;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    
    private final IUserApi userApi;
    private final IRoleServicePort roleServicePort;
    private final IUserMapper userMapper;
    
    @Override
    public UserResponse handle(UserRequest request) {
        User user = userMapper.toUser(request);
        User createdUser = userApi.createUser(user);
        return userMapper.toUserResponse(createdUser);
    }
    
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userApi.getAllUsers();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
    
    @Override
    public UserRoleResponse getUserRole(UUID userId) {
        User user = userApi.getUserById(userId);
        Role role = roleServicePort.getRoleById(user.getRoleId());
        return new UserRoleResponse(role.getId(), role.getName());
    }
} 