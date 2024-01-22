package com.ghkdtlwns987.order.Service;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.QueryOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private QueryOrderRepository queryOrderRepository;
    @InjectMocks
    @Spy
    private QueryOrderServiceImpl queryOrderService;

    Order order1;
    Order order2;
    Order order3;
    OrderRequestDto orderRequestDto1;
    OrderRequestDto orderRequestDto2;
    OrderRequestDto orderRequestDto3;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

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
    void userId가_없어_Order정보_획득_실패(){
        doReturn(true).when(queryOrderService).checkProductIdIsExists(productId1);
        final boolean result = queryOrderService.checkProductIdIsExists(productId1);
        assertThat(result).isEqualTo(true);
    }
}
