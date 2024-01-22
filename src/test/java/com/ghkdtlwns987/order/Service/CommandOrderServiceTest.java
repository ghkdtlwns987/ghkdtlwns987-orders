package com.ghkdtlwns987.order.Service;


import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.CommandOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Impl.QueryOrderServiceImpl;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandOrderServiceTest {
    private CommandOrderRepository commandOrderRepository;
    private QueryOrderService queryOrderService;
    private CommandOrderServiceImpl commandOrderService;

    OrderRequestDto orderRequestDto;
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";

    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";


    private
    @BeforeEach
    void setUp(){
        commandOrderRepository = Mockito.mock(CommandOrderRepository.class);
        queryOrderService = Mockito.mock(QueryOrderService.class);
        commandOrderService = new CommandOrderServiceImpl(commandOrderRepository, queryOrderService);

        orderRequestDto = new OrderRequestDto(
                productId1,
                qty,
                unitPrice
        );
    }

    @Test
    void userId가_존재하지_않으면_빈_배열_반환() throws Exception{
        // given
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        List<OrderResponseDto> orderResponse = Arrays.asList(
                orderResponseDto
        );
        when(queryOrderService.getOrderByUserId(userId)).thenReturn(orderResponse);

        // when
        List<OrderResponseDto> result = commandOrderService.getOrderInfo(userId);
        assertEquals(orderResponse.size(), result.size());

        // then
        verify(queryOrderService, times(1)).getOrderByUserId(userId);
    }
    @Test
    void 주문_조회_성공() throws Exception {
        final String orderId = UUID.randomUUID().toString();
        LocalDateTime orderedAt = LocalDateTime.now();
        OrderResponseDto orderResponseDto1 = new OrderResponseDto(
                productId1,
                qty,
                unitPrice,
                1000,
                userId,
                orderId,
                orderedAt
        );
        OrderResponseDto orderResponseDto2 = new OrderResponseDto(
                productId2,
                qty,
                unitPrice,
                1000,
                userId,
                orderId,
                orderedAt
        );

        // given
        List<OrderResponseDto> orderResponse = Arrays.asList(
                orderResponseDto1,
                orderResponseDto2
        );

        when(queryOrderService.getOrderByUserId(userId)).thenReturn(orderResponse);

        // when
        List<OrderResponseDto> result = commandOrderService.getOrderInfo(userId);

        // then
        assertEquals(orderResponse.size(), result.size());

        assertThat(orderResponse.get(0).getProductId()).isEqualTo(result.get(0).getProductId());
        assertThat(orderResponse.get(0).getQty()).isEqualTo(result.get(0).getQty());
        assertThat(orderResponse.get(0).getUnitPrice()).isEqualTo(result.get(0).getUnitPrice());
        assertThat(orderResponse.get(0).getTotalPrice()).isEqualTo(result.get(0).getTotalPrice());
        assertThat(orderResponse.get(0).getUserId()).isEqualTo(result.get(0).getUserId());
        assertThat(orderResponse.get(0).getOrderId()).isEqualTo(result.get(0).getOrderId());
        assertThat(orderResponse.get(0).getOrderedAt()).isEqualTo(result.get(0).getOrderedAt());

        assertThat(orderResponse.get(1).getProductId()).isEqualTo(result.get(1).getProductId());
        assertThat(orderResponse.get(1).getQty()).isEqualTo(result.get(1).getQty());
        assertThat(orderResponse.get(1).getUnitPrice()).isEqualTo(result.get(1).getUnitPrice());
        assertThat(orderResponse.get(1).getTotalPrice()).isEqualTo(result.get(1).getTotalPrice());
        assertThat(orderResponse.get(1).getUserId()).isEqualTo(result.get(1).getUserId());
        assertThat(orderResponse.get(1).getOrderId()).isEqualTo(result.get(1).getOrderId());
        assertThat(orderResponse.get(1).getOrderedAt()).isEqualTo(result.get(1).getOrderedAt());

        verify(queryOrderService, times(1)).getOrderByUserId(userId);
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
    void 주문_성공() throws Exception{
        // given
        Order savedOrder = Order.builder()
                .productId(productId1)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(1000)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();

        doReturn(false).when(queryOrderService).orderExistsByProductId(productId1);
        when(commandOrderRepository.save(any(Order.class))).thenReturn(savedOrder);
        // when
        OrderResponseDto response = commandOrderService.createOrder(userId, orderRequestDto);

        // then
        assertThat(response.getProductId()).isEqualTo(savedOrder.getProductId());
        assertThat(response.getQty()).isEqualTo(savedOrder.getQty());
        assertThat(response.getUnitPrice()).isEqualTo(savedOrder.getUnitPrice());
        assertThat(response.getTotalPrice()).isEqualTo(savedOrder.getTotalPrice());
        assertThat(response.getUserId()).isEqualTo(savedOrder.getUserId());
        assertThat(response.getOrderId()).isEqualTo(savedOrder.getOrderId());

        verify(commandOrderRepository, times(1)).save(any());
    }
}
