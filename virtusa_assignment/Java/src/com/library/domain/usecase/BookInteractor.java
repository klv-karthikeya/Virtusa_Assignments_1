package com.library.domain.usecase;

import com.library.domain.entity.Book;
import com.library.domain.repository.IBookRepository;
import java.util.List;

public class BookInteractor implements IBookUseCase {
    private final IBookRepository bookRepository;

    public BookInteractor(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(String id, String title, String author, String isbn) {
        bookRepository.save(new Book(id, title, author, isbn));
    }

    @Override
    public boolean removeBook(String id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.search(query);
    }
}
