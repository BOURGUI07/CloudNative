package com.polarbookshop.catalogservice.mapper;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toEntity(BookRequest bookRequest) {
        return new Book()
                .setIsbn(bookRequest.isbn())
                .setTitle(bookRequest.title())
                .setAuthor(bookRequest.author())
                .setPrice(bookRequest.price());
    }

    public BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getIsbn(),book.getTitle(),book.getAuthor(), book.getPrice(),book.getCreatedDate(),book.getLastModifiedDate(),book.getVersion());
    }
}
