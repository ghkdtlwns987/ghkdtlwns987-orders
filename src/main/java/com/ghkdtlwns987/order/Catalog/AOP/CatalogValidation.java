package com.ghkdtlwns987.order.Catalog.AOP;

import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Rest.Query.QueryCatalog;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.rmi.ServerException;

@Aspect
@Component
@RequiredArgsConstructor
public class CatalogValidation {
    private final QueryCatalog queryCatalog;
    @Before("execution(* com.ghkdtlwns987.order.Service.Inter.CommandOrderService.createOrder(..)) " + "&& args(userId, orderRequestDto)")
    private void createOrderValidation(String userId, OrderRequestDto orderRequestDto) throws ServerException {
        ResponseOrderForCatalogDto response = queryCatalog.getCategoriesByProductIdRequest(orderRequestDto.getProductId());
        if(response.getStock() < orderRequestDto.getQty()){
            throw new ClientException(ErrorCode.OUT_OF_STOCK, ErrorCode.OUT_OF_STOCK.getMessage());
        }
    }
}
