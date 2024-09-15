package com.polarbookshop.order_service.mapper;

import com.polarbookshop.order_service.domain.Order;
import com.polarbookshop.order_service.dto.OrderRequest;
import com.polarbookshop.order_service.dto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest orderRequest) {
        return new Order()
                .setBookIsbn(orderRequest.bookIsbn())
                .setQuantity(orderRequest.quantity());
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(),
                                order.getBookIsbn(),
                                order.getBookName(),
                                order.getBookPrice(),
                                order.getQuantity(),
                                order.getStatus(),
                                order.getCreatedDate(),
                                order.getLastModifiedDate(),
                                order.getVersion());
    }
}
