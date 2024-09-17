package com.polarbookshop.order_service.service;

import com.polarbookshop.order_service.client.BookClient;
import com.polarbookshop.order_service.domain.Order;
import com.polarbookshop.order_service.domain.OrderStatus;
import com.polarbookshop.order_service.dto.BookDTO;
import com.polarbookshop.order_service.dto.OrderRequest;
import com.polarbookshop.order_service.dto.OrderResponse;
import com.polarbookshop.order_service.event.AcceptedOrderMessage;
import com.polarbookshop.order_service.event.DispatchedOrderMessage;
import com.polarbookshop.order_service.mapper.OrderMapper;
import com.polarbookshop.order_service.repo.OrderRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
@Slf4j
public class OrderService {
    OrderRepo orderRepo;
    OrderMapper orderMapper;
    BookClient bookClient;
    StreamBridge streamBridge;
    public Flux<OrderResponse> getAllOrders() {
        return orderRepo.findAll().map(orderMapper::toResponse);
    }
    @Transactional
    public Mono<OrderResponse> submitOrder(OrderRequest orderRequest) {
        return bookClient.getBook(orderRequest.bookIsbn())
                .map(book -> submitAcceptedOrder(book, orderRequest.quantity()))
                .defaultIfEmpty(submitRejectedOrder(orderRequest))
                .flatMap(orderRepo::save)
                .map(orderMapper::toResponse)
                .doOnNext(this::publishOrderAcceptedEvent);

    }

    public Flux<OrderResponse> consumeDispatchedOrderEvent(Flux<DispatchedOrderMessage> flux) {
        return flux.flatMap(message-> orderRepo.findById(message.orderId()))
                .map(this::buildDispatchedOrder)
                .flatMap(orderRepo::save)
                .map(orderMapper::toResponse);

    }

    private Order buildDispatchedOrder(Order order) {
        return order.setStatus(OrderStatus.DISPATCHED);
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

    private void publishOrderAcceptedEvent(OrderResponse orderResponse) {
        if(!orderResponse.status().equals(OrderStatus.ACCEPTED)) {
            return;
        }
        var acceptedOrder = new AcceptedOrderMessage(orderResponse.orderId());
        log.info("Sending Accepted Order Event with id: {}", orderResponse.orderId());
        var result = streamBridge.send("acceptOrder-out-0", acceptedOrder);
        log.info("Result for sending data for order with id {}: {}", orderResponse.orderId(), result);
    }
}
