package com.library.data.repository;

import com.library.domain.entity.Book;
import com.library.domain.repository.IBookRepository;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements IBookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) { books.put(book.getId(), book); }
    @Override
    public void delete(String id) { books.remove(id); }
    @Override
    public Optional<Book> findById(String id) { return Optional.ofNullable(books.get(id)); }
    @Override
    public List<Book> findAll() { return new ArrayList<>(books.values()); }
    @Override
    public List<Book> search(String query) {
        String l = query.toLowerCase();
        return books.values().stream().filter(b -> b.getTitle().toLowerCase().contains(l) || b.getAuthor().toLowerCase().contains(l)).collect(Collectors.toList());
    }
}
