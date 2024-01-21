package com.ghkdtlwns987.order.Service.Inter;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getOrderByUserId(String userId) throws Exception;

    OrderResponseDto getOrderByOrderId(String orderId) throws Exception;
    OrderResponseDto createOrder(String userID, OrderRequestDto orderRequestDto) throws Exception;
}
