package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Entity.Order;
import org.springframework.data.repository.CrudRepository;


public interface CommandOrderRepository extends CrudRepository<Order, Long> {
    Order save(Order oRder);

}
