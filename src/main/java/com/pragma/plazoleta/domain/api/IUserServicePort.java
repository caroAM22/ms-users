package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.User;
import java.util.List;
import java.util.UUID;

public interface IUserServicePort {
    User createUser(User user, String creatorRoleName);
    List<User> getAllUsers();
    User getUserById(UUID userId);
} 