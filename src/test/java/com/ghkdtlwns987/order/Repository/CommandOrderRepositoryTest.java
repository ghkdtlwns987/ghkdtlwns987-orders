package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@DataJpaTest
public class CommandOrderRepositoryTest {
    @Autowired
    CommandOrderRepository commandOrderRepository;

    private final String orderId1 = "CATALOG-0001";
    private final String orderId2 = "CATALOG-0002";
    private final String orderId3 = "CATALOG-0003";

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
                orderId1,
                qty,
                unitPrice
        );

        orderRequestDto2 = new OrderRequestDto(
                orderId2,
                qty,
                unitPrice
        );
        orderRequestDto3 = new OrderRequestDto(
                orderId3,
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
        Order savedOrder = commandOrderRepository.save(order1);

        // then
        assertThat(savedOrder.getOrderId()).isEqualTo(order1.getOrderId());
        assertThat(savedOrder.getProductId()).isEqualTo(order1.getProductId());
        assertThat(savedOrder.getQty()).isEqualTo(order1.getQty());
        assertThat(savedOrder.getTotalPrice()).isEqualTo(order1.getTotalPrice());
        assertThat(savedOrder.getUnitPrice()).isEqualTo(order1.getUnitPrice());
    }

    @Test
    void 주문_검색_실패_orderId가_존재하지_않음() throws Exception{
        Order savedOrder1 = commandOrderRepository.save(order1);
        Order savedOrder2 = commandOrderRepository.save(order2);
        Order savedOrder3 = commandOrderRepository.save(order3);

        Order foundOrder = commandOrderRepository.findOrderByOrderId("CATALOG-0010");



    }
    @Test
    void 주문_검색_테스트() throws Exception{
        // given
        Order savedOrder1 = commandOrderRepository.save(order1);
        Order savedOrder2 = commandOrderRepository.save(order2);
        Order savedOrder3 = commandOrderRepository.save(order3);

        // then
        Order foundOrder = commandOrderRepository.findOrderByOrderId(savedOrder2.getOrderId());

        assertThat(foundOrder.getOrderId()).isEqualTo(order2.getOrderId());
        assertThat(foundOrder.getProductId()).isEqualTo(order2.getProductId());
        assertThat(foundOrder.getQty()).isEqualTo(order2.getQty());
        assertThat(foundOrder.getTotalPrice()).isEqualTo(order2.getTotalPrice());
        assertThat(foundOrder.getUnitPrice()).isEqualTo(order2.getUnitPrice());
    }

}
