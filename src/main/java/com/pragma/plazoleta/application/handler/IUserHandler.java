package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;

public interface IUserHandler {
    UserResponse createUser(UserRequest request, String creatorRoleName);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID userId);
} 