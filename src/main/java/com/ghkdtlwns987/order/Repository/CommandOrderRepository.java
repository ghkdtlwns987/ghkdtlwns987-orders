package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Entity.Order;
import org.springframework.data.repository.CrudRepository;


public interface CommandOrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findOrderByUserId(String userId);
    Order findOrderByOrderId(String orderId);

    boolean checkOrderByOrderId(String orderId);

    Order save(Order oRder);

}
