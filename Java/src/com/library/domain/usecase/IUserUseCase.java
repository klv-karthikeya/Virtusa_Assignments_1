package com.library.domain.usecase;

import com.library.domain.entity.User;
import java.util.List;

public interface IUserUseCase {
    void registerUser(String id, String name, String email);
    List<User> getAllUsers();
}
