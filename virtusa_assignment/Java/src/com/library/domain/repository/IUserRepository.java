package com.library.domain.repository;

import com.library.domain.entity.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    void save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
}
