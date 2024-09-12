package com.polarbookshop.catalogservice.dto;

public record BookResponse(
        String isbn,
        String title,
        String author,
        Double price
) {
}
