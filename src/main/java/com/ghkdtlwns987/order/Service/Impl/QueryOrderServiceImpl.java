package com.ghkdtlwns987.order.Service.Impl;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryOrderServiceImpl implements QueryOrderService {
    private final QueryOrderRepository queryOrderRepository;
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderByUserId(String userId) {
        Iterable<Order> orders = queryOrderRepository.findOrderByUserId(userId);
        List<Order> orderList = new ArrayList<>();
        orders.forEach(orderList::add);

        return orderList.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderByProductId(String productId) {
        return queryOrderRepository.findOrderByProductId(productId)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.PRODUCT_ID_NOT_EXISTS,
                        "Product Id : " + productId
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkProductIdIsExists(String productId) {
        return queryOrderRepository.existsOrderByProductId(productId);
    }
}
