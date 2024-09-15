package com.polarbookshop.order_service.repo;

import com.polarbookshop.order_service.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends ReactiveCrudRepository<Order, Integer> {
}
