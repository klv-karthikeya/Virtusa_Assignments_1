package com.library.domain.entity;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String isbn;
    private boolean isAvailable;

    public Book(String id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("ID: %s | Title: %s | Author: %s | ISBN: %s | Available: %s",
                id, title, author, isbn, isAvailable ? "Yes" : "No");
    }
}
