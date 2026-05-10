package com.library.data.repository;

import com.library.domain.entity.User;
import com.library.domain.repository.IUserRepository;
import java.util.*;

public class InMemoryUserRepository implements IUserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) { users.put(user.getId(), user); }
    @Override
    public Optional<User> findById(String id) { return Optional.ofNullable(users.get(id)); }
    @Override
    public List<User> findAll() { return new ArrayList<>(users.values()); }
}
