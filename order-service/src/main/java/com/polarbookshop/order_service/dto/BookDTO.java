package com.polarbookshop.order_service.dto;

public record BookDTO(
        String isbn,
        String title,
        String author,
        Double price
) {
}
