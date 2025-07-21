package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.User;
import java.util.List;

public interface IUserPersistencePort {
    User saveUser(User user);
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(Long documentNumber);
    List<User> getAllUsers();
} 