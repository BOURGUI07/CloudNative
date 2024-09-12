package com.polarbookshop.catalogservice.repo;

import com.polarbookshop.catalogservice.domain.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    @Transactional
    @Modifying
    void deleteByIsbn(String isbn);
}
