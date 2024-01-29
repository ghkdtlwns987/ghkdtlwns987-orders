package com.ghkdtlwns987.order.AOP;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Exception.Class.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.order.Repository.QueryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommandOrderServiceValidation {
    private final QueryOrderRepository queryOrderRepository;
    @Before("execution(* com.ghkdtlwns987.order.Service.Inter.CommandOrderService.createOrder(..)) " + "&& args(userId, orderRequestDto)")
    private void createOrderValidation(String userId, OrderRequestDto orderRequestDto) {
        if(queryOrderRepository.existsOrderByProductId(orderRequestDto.getProductId())){
            throw new ProductIdAlreadyExistsException();
        }
    }
}
