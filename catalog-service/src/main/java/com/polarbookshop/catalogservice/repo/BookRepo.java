package com.polarbookshop.catalogservice.repo;

import com.polarbookshop.catalogservice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    @Modifying
    @Transactional
    void deleteByIsbn(String isbn);
}
