package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
import com.pragma.plazoleta.application.mapper.IUserMapper;
import com.pragma.plazoleta.domain.api.IUserApi;
import com.pragma.plazoleta.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    
    private final IUserApi userApi;
    private final IUserMapper userMapper;
    
    @Override
    public UserResponse handle(UserRequest request) {
        User createdUser = userApi.createUser(userMapper.toUser(request));
        return userMapper.toUserResponse(createdUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userApi.getAllUsers();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
} 