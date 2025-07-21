package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import java.util.List;

public interface IUserHandler {
    UserResponse handle(UserRequest request);
    List<UserResponse> getAllUsers();
} 