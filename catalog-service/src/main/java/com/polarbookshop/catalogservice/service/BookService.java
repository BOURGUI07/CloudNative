package com.polarbookshop.catalogservice.service;

import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.mapper.BookMapper;
import com.polarbookshop.catalogservice.repo.BookRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BookService {
    BookRepo bookRepo;
    BookMapper bookMapper;

    public BookResponse getByIsbn(
            @NotBlank(message = "Book ISBN is Required")
            @Pattern(regexp="^([0-9]{10}|[0-9]{13})$")
            String isbn) {
        return bookRepo.findByIsbn(isbn)
                .map(bookMapper::toResponse)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<BookResponse> getAll() {
        return bookRepo.findAll().stream().map(bookMapper::toResponse).toList();
    }

    public BookResponse create(BookRequest bookRequest) {
        if(bookRepo.existsByIsbn(bookRequest.isbn())){
            throw new BookAlreadyExistsException(bookRequest.isbn());
        }
        var book = bookMapper.toEntity(bookRequest);
        var savedBook = bookRepo.save(book);
        return bookMapper.toResponse(savedBook);
    }

    public BookResponse update(
            @NotBlank(message = "Book ISBN is Required")
            @Pattern(regexp="^([0-9]{10}|[0-9]{13})$")
            String isbn, BookRequest bookRequest) {
        var book = bookRepo.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn))
                .setAuthor(bookRequest.author())
                .setIsbn(bookRequest.isbn())
                .setPrice(bookRequest.price())
                .setTitle(bookRequest.title());
        var updatedBook = bookRepo.save(book);
        return bookMapper.toResponse(updatedBook);

    }

    public void delete(
            @NotBlank(message = "Book ISBN is Required")
            @Pattern(regexp="^([0-9]{10}|[0-9]{13})$")
            String isbn) {
        bookRepo.deleteByIsbn(isbn);
    }
}
