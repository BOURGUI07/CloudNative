package com.polarbookshop.dispatcher_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@Configuration
public class DispatchingFunctions {
    @Bean
    public Function<OrderAcceptedMessage,Integer> pack(){
        return packedOrder -> {
            log.info("Order with id {} is packed",packedOrder.orderId());
            return packedOrder.orderId();
        };
    }

    @Bean
    public Function<Flux<Integer>,Flux<DispatchedOrderMessage>> label(){
        return orderFlux -> orderFlux.map(
                orderId -> {
                    log.info("Order with id {} is labeled",orderId);
                    return new DispatchedOrderMessage(orderId);
                }
        );
    }
}
