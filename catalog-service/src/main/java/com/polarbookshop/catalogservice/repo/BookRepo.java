package com.polarbookshop.catalogservice.repo;

import com.polarbookshop.catalogservice.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepo {
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
    Book save(Book book);
    List<Book> findAll();
}
