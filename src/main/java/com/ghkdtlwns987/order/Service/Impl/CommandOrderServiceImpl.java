package com.ghkdtlwns987.order.Service.Impl;

import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Rest.Command.CommandCatalog;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Entity.Order;
import com.ghkdtlwns987.order.Repository.CommandOrderRepository;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class CommandOrderServiceImpl implements CommandOrderService {
    private final CommandOrderRepository commandOrderRepository;
    private final CommandCatalog commandCatalog;
    @Override
    public OrderResponseDto createOrder(String userId, OrderRequestDto orderRequestDto) throws ServerException {
        commandCatalog.createOrderRequestForCatalog(orderRequestDto.toCatalog());
        Order order = orderRequestDto.toEntity(userId);
        Order response = commandOrderRepository.save(order);
        return OrderResponseDto.fromEntity(response);
    }
}
