package com.polarbookshop.order_service.event;

public record DispatchedOrderMessage(
        Integer orderId
) {
}
