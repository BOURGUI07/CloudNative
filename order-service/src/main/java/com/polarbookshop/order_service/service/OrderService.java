package com.polarbookshop.order_service.service;

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

    public Flux<OrderResponse> getAllOrders() {
        return orderRepo.findAll().map(orderMapper::toResponse);
    }

    public Mono<OrderResponse> submitOrder(OrderRequest orderRequest) {
        var order = orderMapper.toEntity(orderRequest);
        return orderRepo.save(order).map(orderMapper::toResponse);
    }
}
