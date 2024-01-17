package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findOrderByUserId(String orderId);
    Order findOrderByOrderId(String orderId);
}
