package com.ghkdtlwns987.order.Service;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Persistent.JpaOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.QueryOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EntityManager;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class QueryOrderServiceTest {

    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";

    private QueryOrderRepository queryOrderRepository;
    private QueryOrderServiceImpl queryOrderService;

    Order order1;
    Order order2;
    Order order3;
    OrderRequestDto orderRequestDto1;
    OrderRequestDto orderRequestDto2;
    OrderRequestDto orderRequestDto3;

    @BeforeEach
    void setUp(){
        queryOrderRepository = Mockito.mock(QueryOrderRepository.class);
        queryOrderService = new QueryOrderServiceImpl(queryOrderRepository);

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

        order1 = Order.builder()
                .Id(1L)
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();
        order2 = Order.builder()
                .Id(2L)
                .productId(productId2)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();
        order3 = Order.builder()
                .Id(3L)
                .productId(productId3)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();
    }


    @Test
    void userId를_기반으로_Order_검색_실패(){
        // given
        final String invalid_userId = "invalid-user-id-1512-325";
        when(queryOrderRepository.findOrderByUserId(invalid_userId))
                .thenThrow(ClientException.class);

        // when then
        assertThatThrownBy(() -> queryOrderService.getOrderByUserId(invalid_userId))
                .isInstanceOf(ClientException.class);
    }

    @Test
    void userId를_기반으로_Order_검색_성공(){
        // given
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        when(queryOrderRepository.findOrderByUserId(userId)).thenReturn(orders);

        // when
        List<OrderResponseDto> result = queryOrderService.getOrderByUserId(userId);

        // then
        assertEquals(orders.size(), result.size());
    }
    @Test
    void productId가_존재하지_않아_Order_검색_실패(){
        // given
        final String invalid_product_Id = "CATALOG-00444";
        when(queryOrderRepository.findOrderByProductId(invalid_product_Id))
                .thenThrow(ClientException.class);

        // when then
        assertThatThrownBy(() -> queryOrderService.getOrderByProductId(invalid_product_Id))
                .isInstanceOf(ClientException.class);
    }

    @Test
    void productId가_존재하지_않아_Order_검색_성공(){
        // given
        Order order;
        when(queryOrderRepository.findOrderByProductId(productId1)).thenReturn(Optional.of(order1));

        // when
        order = queryOrderService.getOrderByProductId(productId1);

        // then
        assertThat(order.getProductId()).isEqualTo(order1.getProductId());
    }

    @Test
    void productId_존재_검색_실패(){
        // given
        final String invalid_productId = "invalid-product-id";
        when(queryOrderRepository.existsOrderByProductId(invalid_productId)).thenReturn(false);

        // when
        boolean result = queryOrderService.orderExistsByProductId(invalid_productId);

        // then
        assertThat(result).isEqualTo(false);
    }
    @Test
    void productId_존재_검색_성공(){
        // given
        when(queryOrderRepository.existsOrderByProductId(productId1)).thenReturn(true);

        // when
        boolean result = queryOrderService.orderExistsByProductId(productId1);

        // then
        assertThat(result).isEqualTo(true);
    }
}
