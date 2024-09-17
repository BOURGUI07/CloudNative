package com.polarbookshop.order_service.event;

import com.polarbookshop.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrderFunctions {
    @Bean
    public Consumer<Flux<DispatchedOrderMessage>> dispatchOrder(OrderService orderService) {
        return flux -> orderService.consumeDispatchedOrderEvent(flux)
                .doOnNext(order -> log.info("Consumed dispatched order: {}", order.orderId()))
                .subscribe();
    }
}
