package com.polarbookshop.catalogservice.repo;

import com.polarbookshop.catalogservice.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBookRepo implements BookRepo {
    private final static Map<String,Book> books = new ConcurrentHashMap<>();
    @Override
    public boolean existsByIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.containsKey(isbn)?Optional.of(books.get(isbn)):Optional.empty();
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }

    @Override
    public Book save(Book book) {
        books.put(book.getIsbn(),book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return books.values().stream().toList();
    }

    @Override
    public void saveAll(Book... books) {
        Arrays.stream(books).forEach(this::save);
    }
}
