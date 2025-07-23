package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.dto.response.UserRoleResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserServicePort;
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
    
    private final IUserServicePort userApi;
    private final IRoleServicePort roleServicePort;
    private final IUserMapper userMapper;
    
    @Override
    public UserResponse handle(UserRequest request) {
        User user = userMapper.toUser(request);
        User createdUser = userApi.createUser(user);
        Role role = roleServicePort.getRoleById(createdUser.getRoleId());
        UserRoleResponse roleResponse = new UserRoleResponse(role.getId(), role.getName());
        return userMapper.toUserResponse(createdUser, roleResponse);
    }
    
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userApi.getAllUsers();
        return users.stream()
                .map(user -> {
                    Role role = roleServicePort.getRoleById(user.getRoleId());
                    UserRoleResponse roleResponse = new UserRoleResponse(role.getId(), role.getName());
                    return userMapper.toUserResponse(user, roleResponse);
                })
                .toList();
    }
    
    @Override
    public UserResponse getUserById(UUID userId) {
        User user = userApi.getUserById(userId);
        Role role = roleServicePort.getRoleById(user.getRoleId());
        UserRoleResponse roleResponse = new UserRoleResponse(role.getId(), role.getName());
        return userMapper.toUserResponse(user, roleResponse);
    }
} 