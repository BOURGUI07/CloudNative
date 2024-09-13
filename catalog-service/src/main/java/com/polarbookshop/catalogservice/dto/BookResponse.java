package com.polarbookshop.catalogservice.dto;

import java.time.Instant;

public record BookResponse(
        Integer id,
        String isbn,
        String title,
        String author,
        Double price,
        Instant createdDate,
        Instant lastModifiedDate,
        Integer version
) {
}
