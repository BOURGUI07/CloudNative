package com.polarbookshop.dispatcher_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class FunctionsStreamIntegrationTests {
    @Autowired
    private InputDestination input;
    @Autowired
    private OutputDestination output;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void whenOrderAcceptedThenDispatched() throws Exception {
        var orderId = 122;
        var inputMessage = MessageBuilder.withPayload(new OrderAcceptedMessage(orderId)).build();
        var expectedMessage = MessageBuilder.withPayload(new DispatchedOrderMessage(orderId)).build();
        input.send(inputMessage);
        assertThat(mapper.readValue(output.receive().getPayload(), DispatchedOrderMessage.class)).isEqualTo(expectedMessage.getPayload());
    }
}
