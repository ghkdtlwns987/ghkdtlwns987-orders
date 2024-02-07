package com.ghkdtlwns987.order.Service.Inter;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;

import java.rmi.ServerException;
import java.util.List;

public interface CommandOrderService {
    OrderResponseDto createOrder(String userId, OrderRequestDto orderRequestDto) throws ServerException;
}
