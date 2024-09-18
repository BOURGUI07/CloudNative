package com.polarbookshop.order_service.controller;

import com.polarbookshop.order_service.dto.OrderRequest;
import com.polarbookshop.order_service.dto.OrderResponse;
import com.polarbookshop.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(value = "/api/v1/orders",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class OrderControllerV1 {
    OrderService orderService;

    @GetMapping
    public ResponseEntity<Flux<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<OrderResponse>> submitOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.submitOrder(orderRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<OrderResponse>> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}
