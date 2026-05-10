package com.library.data.repository;

import com.library.data.database.DatabaseConnection;
import com.library.domain.entity.Transaction;
import com.library.domain.repository.ITransactionRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MySQLTransactionRepository implements ITransactionRepository {
    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, book_id, user_id, issue_date, due_date, return_date, fine_amount) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE return_date=?, fine_amount=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getBookId());
            stmt.setString(3, transaction.getUserId());
            stmt.setObject(4, transaction.getIssueDate());
            stmt.setObject(5, transaction.getDueDate());
            stmt.setObject(6, transaction.getReturnDate());
            stmt.setDouble(7, transaction.getFineAmount());
            stmt.setObject(8, transaction.getReturnDate());
            stmt.setDouble(9, transaction.getFineAmount());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction t = new Transaction(rs.getString("id"), rs.getString("book_id"), rs.getString("user_id"), 
                                                rs.getObject("issue_date", LocalDate.class), rs.getObject("due_date", LocalDate.class));
                t.setReturnDate(rs.getObject("return_date", LocalDate.class));
                t.setFineAmount(rs.getDouble("fine_amount"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public Optional<Transaction> findActiveByBookId(String bookId) {
        String sql = "SELECT * FROM transactions WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction t = new Transaction(rs.getString("id"), rs.getString("book_id"), rs.getString("user_id"), 
                                                rs.getObject("issue_date", LocalDate.class), rs.getObject("due_date", LocalDate.class));
                return Optional.of(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }
}
