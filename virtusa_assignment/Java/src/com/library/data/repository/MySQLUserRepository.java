package com.library.data.repository;

import com.library.data.database.DatabaseConnection;
import com.library.domain.entity.User;
import com.library.domain.repository.IUserRepository;
import java.sql.*;
import java.util.*;

public class MySQLUserRepository implements IUserRepository {
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (id, name, email) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name=?, email=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(rs.getString("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new User(rs.getString("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
