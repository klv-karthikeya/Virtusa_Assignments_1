package com.library.domain.usecase;

import com.library.domain.entity.Book;
import java.util.List;

public interface IBookUseCase {
    void addBook(String id, String title, String author, String isbn);
    boolean removeBook(String id);
    List<Book> getAllBooks();
    List<Book> searchBooks(String query);
}
