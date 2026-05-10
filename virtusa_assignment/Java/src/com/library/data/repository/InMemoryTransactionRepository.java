package com.library.data.repository;

import com.library.domain.entity.Transaction;
import com.library.domain.repository.ITransactionRepository;
import java.util.*;

public class InMemoryTransactionRepository implements ITransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        transactions.removeIf(t -> t.getId().equals(transaction.getId()));
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> findAll() { return new ArrayList<>(transactions); }

    @Override
    public Optional<Transaction> findActiveByBookId(String bookId) {
        return transactions.stream().filter(t -> t.getBookId().equals(bookId) && !t.isReturned()).findFirst();
    }
}
