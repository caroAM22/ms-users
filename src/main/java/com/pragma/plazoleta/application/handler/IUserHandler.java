package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.dto.response.UserRoleResponse;
import java.util.List;
import java.util.UUID;

public interface IUserHandler {
    UserResponse handle(UserRequest request);
    List<UserResponse> getAllUsers();
    UserRoleResponse getUserRole(UUID userId);
} 