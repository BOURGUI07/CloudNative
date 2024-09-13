package com.polarbookshop.catalogservice.service;

import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import com.polarbookshop.catalogservice.exceptions.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import com.polarbookshop.catalogservice.exceptions.CustomOptimisticLockingFailureException;
import com.polarbookshop.catalogservice.mapper.BookMapper;
import com.polarbookshop.catalogservice.repo.BookRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
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

    @Transactional
    public BookResponse create(BookRequest bookRequest) {
        if(bookRepo.existsByIsbn(bookRequest.isbn())){
            throw new BookAlreadyExistsException(bookRequest.isbn());
        }
        var book = bookMapper.toEntity(bookRequest);
        var savedBook = bookRepo.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Transactional
    public BookResponse update(
            @NotBlank(message = "Book ISBN is Required")
            @Pattern(regexp="^([0-9]{10}|[0-9]{13})$")
            String isbn, BookRequest bookRequest) {
        var book = bookRepo.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn))
                .setAuthor(bookRequest.author())
                .setPrice(bookRequest.price())
                .setTitle(bookRequest.title())
                .setIsbn(bookRequest.isbn());
        try{
            var updatedBook = bookRepo.save(book);
            return bookMapper.toResponse(updatedBook);
        }catch(OptimisticLockingFailureException ex){
            throw new CustomOptimisticLockingFailureException(isbn);
        }


    }
    @Transactional
    public void delete(
            @NotBlank(message = "Book ISBN is Required")
            @Pattern(regexp="^([0-9]{10}|[0-9]{13})$")
            String isbn) {
        bookRepo.deleteByIsbn(isbn);
    }
}
