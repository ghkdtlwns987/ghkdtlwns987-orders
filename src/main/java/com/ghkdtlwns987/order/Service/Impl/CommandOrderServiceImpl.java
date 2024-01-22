package com.ghkdtlwns987.order.Service.Impl;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandOrderServiceImpl implements CommandOrderService {
    private final CommandOrderRepository commandOrderRepository;
    private final QueryOrderService queryOrderService;
    @Override
    @Transactional
    public OrderResponseDto createOrder(String userId, OrderRequestDto orderRequestDto) throws Exception{
        if(productIdAlreadyExists(orderRequestDto)){
            throw new ProductIdAlreadyExistsException();
        }
        Order order = orderRequestDto.toEntity(userId);
        Order response = commandOrderRepository.save(order);

        return OrderResponseDto.fromEntity(response);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderInfo(String userId) throws Exception {
        return queryOrderService.getOrderByUserId(userId);
    }


    private boolean productIdAlreadyExists(OrderRequestDto orderRequestDto){
        if(queryOrderService.orderExistsByProductId(orderRequestDto.getProductId())){
            return true;
        }
        return false;
    }
}
