package com.library.domain.usecase;

import com.library.domain.entity.Book;
import com.library.domain.entity.Transaction;
import com.library.domain.repository.IBookRepository;
import com.library.domain.repository.IUserRepository;
import com.library.domain.repository.ITransactionRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class TransactionInteractor implements ITransactionUseCase {
    private final IBookRepository bookRepository;
    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;
    private static final double FINE_PER_DAY = 10.0;

    public TransactionInteractor(IBookRepository bookRepository, IUserRepository userRepository, ITransactionRepository transactionRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String issueBook(String bookId, String userId, int durationDays) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) return "Error: Book not found.";
        if (userRepository.findById(userId).isEmpty()) return "Error: User not found.";
        
        Book book = bookOpt.get();
        if (!book.isAvailable()) return "Error: Book is already issued.";

        book.setAvailable(false);
        String transactionId = "TXN" + (transactionRepository.findAll().size() + 1);
        LocalDate now = LocalDate.now();
        Transaction transaction = new Transaction(transactionId, bookId, userId, now, now.plusDays(durationDays));
        
        transactionRepository.save(transaction);
        bookRepository.save(book);

        return "Success: Book issued. TX ID: " + transactionId + " | Due: " + transaction.getDueDate();
    }

    @Override
    public String returnBook(String bookId) {
        Optional<Transaction> transactionOpt = transactionRepository.findActiveByBookId(bookId);
        if (transactionOpt.isEmpty()) return "Error: No active transaction for this book.";

        Transaction transaction = transactionOpt.get();
        LocalDate now = LocalDate.now();
        transaction.setReturnDate(now);
        
        double fine = calculateFine(transaction.getDueDate(), now);
        transaction.setFineAmount(fine);

        bookRepository.findById(bookId).ifPresent(b -> {
            b.setAvailable(true);
            bookRepository.save(b);
        });

        transactionRepository.save(transaction);

        String result = "Success: Book returned on " + now;
        if (fine > 0) result += " | Fine: ₹" + String.format("%.2f", fine);
        return result;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, returnDate) * FINE_PER_DAY;
        }
        return 0.0;
    }
}
