package com.library.data.repository;

import com.library.data.database.DatabaseConnection;
import com.library.domain.entity.Book;
import com.library.domain.repository.IBookRepository;
import java.sql.*;
import java.util.*;

public class MySQLBookRepository implements IBookRepository {
    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books (id, title, author, isbn, is_available) VALUES (?, ?, ?, ?, ?) 
                     ON DUPLICATE KEY UPDATE title=?, author=?, isbn=?, is_available=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setBoolean(5, book.isAvailable());
            stmt.setString(6, book.getTitle());
            stmt.setString(7, book.getAuthor());
            stmt.setString(8, book.getIsbn());
            stmt.setBoolean(9, book.isAvailable());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Optional<Book> findById(String id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book b = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                b.setAvailable(rs.getBoolean("is_available"));
                return Optional.of(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book b = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                b.setAvailable(rs.getBoolean("is_available"));
                list.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<Book> search(String query) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String q = "%" + query + "%";
            stmt.setString(1, q);
            stmt.setString(2, q);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book(rs.getString("id"), rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                b.setAvailable(rs.getBoolean("is_available"));
                list.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
