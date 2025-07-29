package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    
    private final IUserServicePort userApi;
    private final IUserMapper userMapper;
    
    @Override
    public UserResponse createUser(UserRequest request) {
        User user = userMapper.toUser(request);
        User createdUser = userApi.createUser(user);
        return userMapper.toUserResponse(createdUser);
    }
    
    @Override
    public UserResponse registerUser(UserRequest request) {
        User user = userMapper.toUser(request);
        User createdUser = userApi.registerUser(user);
        return userMapper.toUserResponse(createdUser);
    }
    
    @Override
    public List<UserResponse> getAllUsers() {
        return userMapper.toUserResponseList(userApi.getAllUsers());
    }
    
    @Override
    public UserResponse getUserById(UUID userId) {
        return userMapper.toUserResponse(userApi.getUserById(userId));
    }
} 