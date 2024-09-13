package com.polarbookshop.catalogservice.controller;

import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import com.polarbookshop.catalogservice.dto.WelcomeMessage;
import com.polarbookshop.catalogservice.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/v1/books",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class BookControllerV1 {
    BookService bookService;
    Environment environment;
    WelcomeMessage welcomeMessage;
    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable @Valid String isbn) {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookRequest));
    }

    @PutMapping(value = "/{isbn}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> update(
            @Valid
            @PathVariable String isbn,
            @Valid
            @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.update(isbn, bookRequest));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable @Valid String isbn) {
        bookService.delete(isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/server-port")
    public ResponseEntity<String> getServerPort() {
        return ResponseEntity.ok(environment.getProperty("server.port"));
    }

    @GetMapping("/welcome")
    public ResponseEntity<WelcomeMessage> getWelcome() {
        return ResponseEntity.ok(welcomeMessage);
    }
}
