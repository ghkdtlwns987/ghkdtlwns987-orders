package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Persistent.JpaOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommandOrderRepositoryTest {
    @Autowired
    JpaOrderRepository commandOrderRepository;

    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";
    private final String productName = "Berlin";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";

    Order order1;
    Order order2;
    Order order3;
    OrderRequestDto orderRequestDto1;
    OrderRequestDto orderRequestDto2;
    OrderRequestDto orderRequestDto3;

    @BeforeEach
    void setUp(){
        orderRequestDto1 = new OrderRequestDto(
                productId1,
                productName,
                qty,
                unitPrice
        );

        orderRequestDto2 = new OrderRequestDto(
                productId2,
                productName,
                qty,
                unitPrice
        );
        orderRequestDto3 = new OrderRequestDto(
                productId3,
                productName,
                qty,
                unitPrice
        );
        order1 = orderRequestDto1.toEntity(userId);
        order2 = orderRequestDto2.toEntity(userId);
        order3 = orderRequestDto3.toEntity(userId);
    }

    @Test
    void 주문등록_성공_테스트() throws Exception{
        // given
        Order savedOrder1 = commandOrderRepository.save(order1);
        Order savedOrder2 = commandOrderRepository.save(order2);
        Order savedOrder3 = commandOrderRepository.save(order3);

        // then
        assertThat(savedOrder1.getOrderId()).isEqualTo(order1.getOrderId());
        assertThat(savedOrder1.getProductId()).isEqualTo(order1.getProductId());
        assertThat(savedOrder1.getQty()).isEqualTo(order1.getQty());
        assertThat(savedOrder1.getTotalPrice()).isEqualTo(order1.getTotalPrice());
        assertThat(savedOrder1.getUnitPrice()).isEqualTo(order1.getUnitPrice());

        assertThat(savedOrder2.getOrderId()).isEqualTo(order2.getOrderId());
        assertThat(savedOrder2.getProductId()).isEqualTo(order2.getProductId());
        assertThat(savedOrder2.getQty()).isEqualTo(order2.getQty());
        assertThat(savedOrder2.getTotalPrice()).isEqualTo(order2.getTotalPrice());
        assertThat(savedOrder2.getUnitPrice()).isEqualTo(order2.getUnitPrice());

        assertThat(savedOrder3.getOrderId()).isEqualTo(order3.getOrderId());
        assertThat(savedOrder3.getProductId()).isEqualTo(order3.getProductId());
        assertThat(savedOrder3.getQty()).isEqualTo(order3.getQty());
        assertThat(savedOrder3.getTotalPrice()).isEqualTo(order3.getTotalPrice());
        assertThat(savedOrder3.getUnitPrice()).isEqualTo(order3.getUnitPrice());
    }
}
