package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.User;
import java.util.List;

public interface IUserApi {
    User createUser(User user);
    List<User> getAllUsers();
} 