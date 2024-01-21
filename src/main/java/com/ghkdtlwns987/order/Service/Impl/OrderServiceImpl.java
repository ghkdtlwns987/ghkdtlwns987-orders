package com.ghkdtlwns987.order.Service.Impl;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CommandOrderRepository commandOrderRepository;
    @Override
    public List<OrderResponseDto> getOrderByUserId(String userId) throws Exception{
        Iterable<Order> orders = commandOrderRepository.findOrderByUserId(userId);
        List<Order> orderList = new ArrayList<>();
        orders.forEach(orderList::add);

        return orderList.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderByOrderId(String orderId) throws Exception{
        Order order = commandOrderRepository.findOrderByOrderId(orderId);
        return OrderResponseDto.fromEntity(order);
    }


    @Override
    public OrderResponseDto createOrder(String userId, OrderRequestDto orderRequestDto) throws Exception{
        if(checkOrderValidation(orderRequestDto.getProductId())){
            throw new ProductIdAlreadyExistsException();
        }
        Order order = orderRequestDto.toEntity(userId);
        Order response = commandOrderRepository.save(order);

        return OrderResponseDto.fromEntity(response);
    }

    private boolean checkOrderValidation(String orderId){
        if(commandOrderRepository.checkOrderByOrderId(orderId)){
            return true;
        }
        return false;
    }
}
