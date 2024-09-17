package com.polarbookshop.order_service.event;

public record AcceptedOrderMessage(
        Integer orderId
) {
}
