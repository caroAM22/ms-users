package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.User;
import java.util.List;
import java.util.UUID;

public interface IUserServicePort {
    int MINIMUM_AGE_REQUIRED=18;
    int MAXIMUM_PHONE_LENGTH=13;

    User createUser(User user, String creatorRoleName);
    User registerUser(User user);
    List<User> getAllUsers();
    User getUserById(UUID userId);
} 