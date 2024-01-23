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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class QueryOrderRepositoryTest extends IntegrationTest {
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";

    Order order1;
    Order order2;
    Order order3;
    OrderRequestDto orderRequestDto1;
    OrderRequestDto orderRequestDto2;
    OrderRequestDto orderRequestDto3;

    @Autowired
    QueryOrderRepository queryOrderRepository;

    @Autowired
    QueryDslQueryOrderRepository queryDslQueryMemberRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp(){
        orderRequestDto1 = new OrderRequestDto(
                productId1,
                qty,
                unitPrice
        );

        orderRequestDto2 = new OrderRequestDto(
                productId2,
                qty,
                unitPrice
        );
        orderRequestDto3 = new OrderRequestDto(
                productId3,
                qty,
                unitPrice
        );
        order1 = orderRequestDto1.toEntity(userId);
        order2 = orderRequestDto2.toEntity(userId);
        order3 = orderRequestDto3.toEntity(userId);

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);
    }

    @Test
    void 주문_검색_실패_productId가_존재하지_않음() throws Exception{
        final String invalidProductId = "CATALOG-1111";
        boolean result = queryOrderRepository.existsOrderByProductId(invalidProductId);
        AssertionsForClassTypes.assertThat(result).isEqualTo(false);
    }


    @Test
    void 주문_검색_실패_userId가_존재하지않아_조회_불가() throws Exception{
        // given
        final String invalidUserId = "invalid-userId-1111";

        // when
        List<Order> foundOrder = queryOrderRepository.findOrderByUserId(invalidUserId);

        List<Order> orderList = new ArrayList<>();
        foundOrder.forEach(orderList::add);
        assertThat(orderList).hasSize(0);
    }
    @Test
    void 주문_검색_테스트() throws Exception{
        // when
        List<Order> foundOrder = queryOrderRepository.findOrderByUserId(userId);

        List<Order> orderList = new ArrayList<>();
        foundOrder.forEach(orderList::add);

        // then
        assertThat(orderList).hasSize(3);

        assertThat(orderList.get(0).getProductId()).isEqualTo(order1.getProductId());
        assertThat(orderList.get(0).getQty()).isEqualTo(order1.getQty());
        assertThat(orderList.get(0).getUnitPrice()).isEqualTo(order1.getUnitPrice());
        assertThat(orderList.get(0).getTotalPrice()).isEqualTo(order1.getTotalPrice());
        assertThat(orderList.get(0).getUserId()).isEqualTo(userId);
        assertThat(orderList.get(0).getOrderId()).isEqualTo(order1.getOrderId());

        assertThat(orderList.get(1).getProductId()).isEqualTo(order2.getProductId());
        assertThat(orderList.get(1).getQty()).isEqualTo(order2.getQty());
        assertThat(orderList.get(1).getUnitPrice()).isEqualTo(order2.getUnitPrice());
        assertThat(orderList.get(1).getTotalPrice()).isEqualTo(order2.getTotalPrice());
        assertThat(orderList.get(1).getUserId()).isEqualTo(userId);
        assertThat(orderList.get(1).getOrderId()).isEqualTo(order2.getOrderId());

        assertThat(orderList.get(2).getProductId()).isEqualTo(order3.getProductId());
        assertThat(orderList.get(2).getQty()).isEqualTo(order3.getQty());
        assertThat(orderList.get(2).getUnitPrice()).isEqualTo(order3.getUnitPrice());
        assertThat(orderList.get(2).getTotalPrice()).isEqualTo(order3.getTotalPrice());
        assertThat(orderList.get(2).getUserId()).isEqualTo(userId);
        assertThat(orderList.get(2).getOrderId()).isEqualTo(order3.getOrderId());
    }
}
