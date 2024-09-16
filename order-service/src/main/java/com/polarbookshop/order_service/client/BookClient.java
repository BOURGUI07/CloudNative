package com.polarbookshop.order_service.client;

import com.polarbookshop.order_service.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookClient {
    private final WebClient webClient;
    private static final String BOOK_ROOT_URL = "/api/v1/books/";
    public Mono<BookDTO> getBook(String isbn) {
        return webClient
                .get()
                .uri(BOOK_ROOT_URL + isbn)
                .retrieve()
                .bodyToMono(BookDTO.class)
                .doOnNext(book -> log.info("Received book: {}", book))
                .doOnError(error -> log.error("Error fetching book: ", error))
                .timeout(Duration.ofSeconds(3),Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class,exception -> Mono.empty())
                .retryWhen(Retry.backoff(3,Duration.ofMillis(100)))
                .onErrorResume(Exception.class,exception -> Mono.empty());
    }
}
