package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.dto.BookRequest;
import com.polarbookshop.catalogservice.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {
	@Autowired
	WebTestClient client;
	BookResponse response = new BookResponse("0123456789","title","author",10.5);
	BookRequest request = new BookRequest("0123456787","title","author",10.5);

	@Test
	void bookCreation() {
		client.post()
				.uri("/api/v1/books")
				.bodyValue(request)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(BookResponse.class)
				.value(actualBook -> {
					assertThat(actualBook).isNotNull();
				});
	}

}
