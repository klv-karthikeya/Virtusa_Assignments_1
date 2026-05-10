package com.library.domain.usecase;

import com.library.domain.entity.Transaction;
import java.util.List;

public interface ITransactionUseCase {
    String issueBook(String bookId, String userId, int durationDays);
    String returnBook(String bookId);
    List<Transaction> getAllTransactions();
}
