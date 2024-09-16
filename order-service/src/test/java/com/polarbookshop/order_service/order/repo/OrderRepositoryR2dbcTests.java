package com.polarbookshop.order_service.order.repo;

import com.polarbookshop.order_service.domain.OrderStatus;
import com.polarbookshop.order_service.dto.OrderRequest;
import com.polarbookshop.order_service.repo.OrderRepo;
import com.polarbookshop.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
public class OrderRepositoryR2dbcTests {
    @Autowired
    private OrderRepo orderRepo;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.12"));

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OrderRepositoryR2dbcTests::r2dbcUrl);
        registry.add("spring.r2dbc.username",  postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
    }

    private static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                postgreSQLContainer.getHost(),
                postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgreSQLContainer.getDatabaseName());
    }

    @Test
    void createRejectOrder(){
        var orderRequest = new OrderRequest("1234567899",4);
        var rejectOrder = OrderService.submitRejectedOrder(orderRequest);
        StepVerifier
                .create(orderRepo.save(rejectOrder))
                .expectNextMatches(o->o.getStatus().equals(OrderStatus.REJECTED))
                .verifyComplete();
    }



}
