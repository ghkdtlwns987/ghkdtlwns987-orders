package com.ghkdtlwns987.order.Service.Inter;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;

import java.util.List;

public interface QueryOrderService {
    List<OrderResponseDto> getOrderByUserId(String userId) throws Exception;

    Order getOrderByProductId(String orderId) throws Exception;

    boolean orderExistsByProductId(String productId);

}
