package com.library.domain.entity;

import java.time.LocalDate;

public class Transaction {
    private final String id;
    private final String bookId;
    private final String userId;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private double fineAmount;

    public Transaction(String id, String bookId, String userId, LocalDate issueDate, LocalDate dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public String getUserId() { return userId; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    public boolean isReturned() { return returnDate != null; }

    @Override
    public String toString() {
        return String.format("TXN: %s | Book: %s | User: %s | Issued: %s | Due: %s | Status: %s | Fine: ₹%.2f",
                id, bookId, userId, issueDate, dueDate, (isReturned() ? "Returned " + returnDate : "Issued"), fineAmount);
    }
}
