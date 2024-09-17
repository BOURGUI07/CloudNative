package com.polarbookshop.dispatcher_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@FunctionalSpringBootTest
class DispatcherServiceApplicationTests {
	@Autowired
	private FunctionCatalog functionCatalog;

	@Test
	void packAndLabelOrder() {
		Function<OrderAcceptedMessage, Flux<DispatchedOrderMessage>> packAndLabel=
				functionCatalog.lookup(Function.class,"pack|label");
		Integer orderId = 122;
		StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
				.expectNextMatches(dispatchedOrderMessage ->
						dispatchedOrderMessage.equals(new DispatchedOrderMessage(orderId)))
				.verifyComplete();

	}

}
