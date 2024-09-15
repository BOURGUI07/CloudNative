package com.polarbookshop.order_service.dto;

import com.polarbookshop.order_service.domain.OrderStatus;

import java.time.Instant;

public record OrderResponse(
        Integer orderId,
        String bookIsbn,
        String bookName,
        Double bookPrice,
        Integer quantity,
        OrderStatus status,
        Instant createdDate,
        Instant lastModifiedDate,
        Integer version
) {
}
