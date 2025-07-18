package com.pragma.plazacomida.application.handler;

import com.pragma.plazacomida.application.dto.request.UserRequestDto;
import com.pragma.plazacomida.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    UserResponseDto saveUser(UserRequestDto userRequestDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
    UserResponseDto getUserByDocumentNumber(String documentNumber);
    void deleteUserById(Long id);
} 