package com.ghkdtlwns987.order.Service;

import com.ghkdtlwns987.order.Catalog.Rest.Command.CommandCatalog;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Service.Impl.CommandOrderServiceImpl;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.rmi.ServerException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandOrderServiceTest {
    private CommandOrderRepository commandOrderRepository;
    private CommandOrderServiceImpl commandOrderService;
    private CommandCatalog commandCatalog;


    OrderRequestDto orderRequestDto;
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";
    private final String productName = "Berlin";
    private final Integer qty = 5;
    private final Integer unitPrice = 1000;
    private final String userId = "c11b7d71-5b23-4a3d-a867-3e97cc8624e3";
    private final String orderId = UUID.randomUUID().toString();

    Order order1;
    Order order2;
    @BeforeEach
    void setUp(){
        commandOrderRepository = Mockito.mock(CommandOrderRepository.class);
        commandCatalog = Mockito.mock(CommandCatalog.class);
        commandOrderService = new CommandOrderServiceImpl(commandOrderRepository, commandCatalog);

        orderRequestDto = new OrderRequestDto(
                productId1,
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
    void 주문_성공() throws ServerException {
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
