package com.ghkdtlwns987.order.Service.Inter;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;

import java.util.List;

public interface CommandOrderService {
    OrderResponseDto createOrder(String userID, OrderRequestDto orderRequestDto) throws Exception;
    List<OrderResponseDto> getOrderInfo(String userId) throws Exception;
}
