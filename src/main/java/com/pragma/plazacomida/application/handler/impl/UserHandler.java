package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.UserRequestDto;
import com.pragma.plazacomida.application.dto.response.UserResponseDto;
import com.pragma.plazacomida.application.handler.IUserHandler;
import com.pragma.plazacomida.application.mapper.IUserRequestMapper;
import com.pragma.plazacomida.application.mapper.IUserResponseMapper;
import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUserModel(userRequestDto);
        // Encode password
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        UserModel savedUser = userServicePort.saveUser(userModel);
        return userResponseMapper.toUserResponseDto(savedUser);
    }
    
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userServicePort.getAllUsers().stream()
                .map(userResponseMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public UserResponseDto getUserById(Long id) {
        UserModel user = userServicePort.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userResponseMapper.toUserResponseDto(user);
    }
    
    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserModel user = userServicePort.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userResponseMapper.toUserResponseDto(user);
    }
    
    @Override
    public UserResponseDto getUserByDocumentNumber(String documentNumber) {
        UserModel user = userServicePort.getUserByDocumentNumber(documentNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userResponseMapper.toUserResponseDto(user);
    }
    
    @Override
    public void deleteUserById(Long id) {
        userServicePort.deleteUserById(id);
    }
} 