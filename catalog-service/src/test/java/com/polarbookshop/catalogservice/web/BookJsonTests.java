package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.dto.BookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testDeserialization() throws Exception {
        var content = """
                {
                    "id": 1,
                    "isbn": "0123456789",
                    "title": "Title",
                    "author": "Author",
                    "price": 10.5
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book("0123456789", "Title", "Author", 10.5));
    }

    @Test
    void testSerialization() throws IOException {
        var book = new Book("0123456789", "Title", "Author", 10.5);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo("0123456789");
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo("Title");
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo("Author");
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(10.5);
    }
}
