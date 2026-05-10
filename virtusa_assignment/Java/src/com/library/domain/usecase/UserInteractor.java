package com.library.domain.usecase;

import com.library.domain.entity.User;
import com.library.domain.repository.IUserRepository;
import java.util.List;

public class UserInteractor implements IUserUseCase {
    private final IUserRepository userRepository;

    public UserInteractor(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(String id, String name, String email) {
        userRepository.save(new User(id, name, email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
