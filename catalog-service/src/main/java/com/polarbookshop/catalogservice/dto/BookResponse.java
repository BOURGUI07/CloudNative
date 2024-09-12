package com.polarbookshop.catalogservice.dto;

public record BookResponse(
        Integer id,
        String isbn,
        String title,
        String author,
        Double price
) {
}
