package com.polarbookshop.catalogservice.mapper;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toEntity(BookRequest bookRequest) {
        return new Book().setAuthor(bookRequest.author())
                .setTitle(bookRequest.title())
                .setIsbn(bookRequest.isbn())
                .setPrice(bookRequest.price());
    }

    public BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getIsbn(),book.getTitle(),book.getAuthor(), book.getPrice());
    }
}
