package com.library.domain.repository;

import com.library.domain.entity.Transaction;
import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findAll();
    Optional<Transaction> findActiveByBookId(String bookId);
}
