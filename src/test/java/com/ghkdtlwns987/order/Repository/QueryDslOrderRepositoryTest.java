package com.ghkdtlwns987.order.Repository;

import com.ghkdtlwns987.order.IntegrationTest;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Persistent.QueryDslQueryOrderRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@Transactional
public class QueryDslOrderRepositoryTest extends IntegrationTest{
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";
    private final String productName = "Berlin";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = UUID.randomUUID().toString();
    private final String orderId1 = UUID.randomUUID().toString();
    private final String orderId2 = UUID.randomUUID().toString();
    private final String orderId3 = UUID.randomUUID().toString();

    Order order1;
    Order order2;
    Order order3;
    OrderRequestDto orderRequestDto1;
    OrderRequestDto orderRequestDto2;
    OrderRequestDto orderRequestDto3;


    @Autowired
    QueryDslQueryOrderRepository queryDslQueryMemberRepository;

    @Autowired
    EntityManager entityManager;

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
        order1 = Order.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(orderId1)
                .build();
        order2 = Order.builder()
                .productId(productId2)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(orderId2)
                .build();
        order3 = Order.builder()
                .productId(productId3)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(orderId3)
                .build();

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);
    }

    @Test
    void 주문_검색_실패_productId가_존재하지_않음(){
        final String invalidProductId = "CATALOG-1111";
        boolean result = queryDslQueryMemberRepository.existsOrderByProductId(invalidProductId);
        AssertionsForClassTypes.assertThat(result).isEqualTo(false);
    }


    @Test
    void 주문_검색_실패_userId가_존재하지않아_조회_불가(){
        // given
        final String invalidUserId = "invalid-userId-1111";

        // when
        List<Order> foundOrder = queryDslQueryMemberRepository.findOrderByUserId(invalidUserId);

        List<Order> orderList = new ArrayList<>();
        foundOrder.forEach(orderList::add);
        assertThat(orderList).hasSize(0);
    }
    @Test
    void 주문_검색_테스트(){
        // when
        List<Order> foundOrder = queryDslQueryMemberRepository.findOrderByUserId(userId);

        List<Order> orderList = new ArrayList<>();
        foundOrder.forEach(orderList::add);

        // then
        assertThat(orderList)
                .isNotEmpty()
                .hasSize(3)
                .extracting(
                        Order::getProductId,
                        Order::getQty,
                        Order::getUnitPrice,
                        Order::getTotalPrice,
                        Order::getUserId,
                        Order::getOrderId
                )
                .containsExactly(
                        tuple(productId1, qty, unitPrice, 1000, userId, orderId1),
                        tuple(productId2, qty, unitPrice, 1000, userId, orderId2),
                        tuple(productId3, qty, unitPrice, 1000, userId, orderId3)
                );
    }
}
