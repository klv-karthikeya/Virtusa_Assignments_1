package com.library.domain.repository;

import com.library.domain.entity.Book;
import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    void save(Book book);
    void delete(String id);
    Optional<Book> findById(String id);
    List<Book> findAll();
    List<Book> search(String query);
}
