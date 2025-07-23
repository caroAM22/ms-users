package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.service.UserPermissionsService;
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
    private final UserPermissionsService userPermissionsService = new UserPermissionsService();
    
    @Override
    public UserResponse handle(UserRequest request, String creatorRoleName) {
        String roleToAssign = userPermissionsService.getRoleToAssign(creatorRoleName);
        UUID roleId = roleServicePort.getRoleIdByName(roleToAssign);
        User user = userMapper.toUser(request);
        user.setRoleId(roleId);
        User createdUser = userApi.createUser(user, creatorRoleName);
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
    public UserResponse getUserById(UUID userId) {
        User user = userApi.getUserById(userId);
        return userMapper.toUserResponse(user);
    }
} 