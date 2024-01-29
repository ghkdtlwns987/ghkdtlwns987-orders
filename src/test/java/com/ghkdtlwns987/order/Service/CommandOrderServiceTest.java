package com.ghkdtlwns987.order.Service;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.CommandOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandOrderServiceTest {
    private CommandOrderRepository commandOrderRepository;
    private QueryOrderRepository queryOrderRepository;
    private QueryOrderService queryOrderService;
    private CommandOrderServiceImpl commandOrderService;

    OrderRequestDto orderRequestDto;
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";
    private final String orderId = UUID.randomUUID().toString();

    Order order1;
    Order order2;
    @BeforeEach
    void setUp(){
        commandOrderRepository = Mockito.mock(CommandOrderRepository.class);
        queryOrderRepository = Mockito.mock(QueryOrderRepository.class);
        queryOrderService = Mockito.mock(QueryOrderService.class);
        commandOrderService = new CommandOrderServiceImpl(commandOrderRepository, queryOrderService);

        orderRequestDto = new OrderRequestDto(
                productId1,
                qty,
                unitPrice
        );

        order1 = Order.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(orderId)
                .build();

        order2 = Order.builder()
                .productId(productId2)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(orderId)
                .build();
    }


    @Test
    void 이미_등록된_주문번호(){
        // given
        doReturn(true).when(queryOrderService).orderExistsByProductId(productId1);

        ProductIdAlreadyExistsException error =
                assertThrows(ProductIdAlreadyExistsException.class,
                        () -> commandOrderService.createOrder(userId, orderRequestDto));

        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.PRODUCT_ID_ALREADY_EXISTS);
        verify(commandOrderRepository, never()).save(any());
    }

    @Test
    void 주문_성공(){
        // given
        Order savedOrder = Order.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();

        when(commandOrderRepository.save(any(Order.class))).thenReturn(savedOrder);
        // when
        OrderResponseDto response = commandOrderService.createOrder(userId, orderRequestDto);


        // then
        Assertions.assertAll(
                () -> assertThat(response.getProductId()).isEqualTo(savedOrder.getProductId()),
                () -> assertThat(response.getQty()).isEqualTo(savedOrder.getQty()),
                () -> assertThat(response.getUnitPrice()).isEqualTo(savedOrder.getUnitPrice()),
                () -> assertThat(response.getTotalPrice()).isEqualTo(savedOrder.getTotalPrice()),
                () -> assertThat(response.getUserId()).isEqualTo(savedOrder.getUserId()),
                () -> assertThat(response.getOrderId()).isEqualTo(savedOrder.getOrderId())
        );

        verify(commandOrderRepository, times(1)).save(any());
    }
}
