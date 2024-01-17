package com.ghkdtlwns987.order.Service.Impl;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.OrderRepository;
import com.ghkdtlwns987.order.Service.Inter.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public List<OrderResponseDto> getOrderByUserId(String userId) {
        Iterable<Order> orders = orderRepository.findOrderByUserId(userId);
        List<Order> orderList = new ArrayList<>();
        orders.forEach(orderList::add);

        return orderList.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        return OrderResponseDto.fromEntity(order);
    }


    @Override
    public OrderResponseDto createOrder(String userId, OrderRequestDto orderRequestDto) {
        Order order = orderRequestDto.toEntity(userId);
        Order response = orderRepository.save(order);

        return OrderResponseDto.fromEntity(response);
    }
}
