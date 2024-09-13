package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.repo.BookRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name="polar.testdata.enabled",havingValue = "true")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class BookDataLoader {
    BookRepo bookRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        var book1 = new Book("0123456789", "title1", "author1", 10.5);
        var book2 = new Book("0123456788", "title2", "author2", 12.5);
        bookRepo.saveAll(book1, book2);
    }
}
