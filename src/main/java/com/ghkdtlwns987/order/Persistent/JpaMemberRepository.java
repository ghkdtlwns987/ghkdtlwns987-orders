package com.ghkdtlwns987.order.Persistent;

import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import org.springframework.data.repository.Repository;

public interface JpaMemberRepository extends Repository<Order, Long>, CommandOrderRepository {

}
