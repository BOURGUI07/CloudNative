package com.polarbookshop.order_service.book;

import com.polarbookshop.order_service.client.BookClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;


@TestMethodOrder(MethodOrderer.Random.class)
public class BookClientTests {
    private MockWebServer server;
    private BookClient bookClient;

    @BeforeEach
    void setup() throws IOException {
        server = new MockWebServer();
        server.start();
        var webClient =  WebClient.builder()
                .baseUrl(server.url("/").uri().toString())
                .build();
        bookClient = new BookClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void whenBookExistsThenReturnBook() {
        var bookIsbn = "1234567899";
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                          "isbn": %s,
                          "title": "title",
                          "author": "author",
                          "price": 9.9
                        } 
                        """.formatted(bookIsbn));
        server.enqueue(mockResponse);
        var book = bookClient.getBook(bookIsbn);
        StepVerifier.create(book)
                .expectNextMatches(b->b.isbn().equals(bookIsbn))
                .expectComplete()
                .verify(Duration.ofSeconds(5));


    }
}
