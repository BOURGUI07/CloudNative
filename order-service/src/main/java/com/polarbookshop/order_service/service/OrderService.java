package com.polarbookshop.order_service.service;

import com.polarbookshop.order_service.client.BookClient;
import com.polarbookshop.order_service.domain.Order;
import com.polarbookshop.order_service.domain.OrderStatus;
import com.polarbookshop.order_service.dto.BookDTO;
import com.polarbookshop.order_service.dto.OrderRequest;
import com.polarbookshop.order_service.dto.OrderResponse;
import com.polarbookshop.order_service.mapper.OrderMapper;
import com.polarbookshop.order_service.repo.OrderRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
public class OrderService {
    OrderRepo orderRepo;
    OrderMapper orderMapper;
    BookClient bookClient;

    public Flux<OrderResponse> getAllOrders() {
        return orderRepo.findAll().map(orderMapper::toResponse);
    }

    public Mono<OrderResponse> submitOrder(OrderRequest orderRequest) {
        return bookClient.getBook(orderRequest.bookIsbn())
                .map(book -> submitAcceptedOrder(book, orderRequest.quantity()))
                .defaultIfEmpty(submitRejectedOrder(orderRequest))
                .flatMap(orderRepo::save)
                .map(orderMapper::toResponse);

    }

    public static Order submitRejectedOrder(OrderRequest orderRequest) {
        return new Order().setBookIsbn(orderRequest.bookIsbn())
                .setQuantity(orderRequest.quantity());
    }

    public static Order submitAcceptedOrder(BookDTO book, Integer quantity) {
        return new Order().setStatus(OrderStatus.ACCEPTED)
                .setQuantity(quantity)
                .setBookName(book.title()+" - "+book.author())
                .setBookPrice(book.price())
                .setBookIsbn(book.isbn());
    }
}
