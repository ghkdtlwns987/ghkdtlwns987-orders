package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Catalog.Dto.RequestOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Dto.ResponseOrderForCatalogDto;
import com.ghkdtlwns987.order.Catalog.Service.Inter.QueryCatalogService;
import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Exception.ClientException;
import com.ghkdtlwns987.order.Exception.ErrorCode;
import com.ghkdtlwns987.order.Global.ResultCode;
import com.ghkdtlwns987.order.Global.ResultResponse;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class CommandOrderController
{
    private final CommandOrderService commandOrderService;
    private final QueryCatalogService queryCatalogService;
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResultResponse> createOrder(@PathVariable String userId, @RequestBody OrderRequestDto request) throws ServerException {
        if(checkIfCatalogExists(request)){
            throw new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage());
        }
        OrderResponseDto result = commandOrderService.createOrder(userId, request);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.ORDER_REQUEST_SUCCESS, result);

        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    public boolean checkIfCatalogExists(OrderRequestDto orderRequestDto) throws ServerException {
        RequestOrderForCatalogDto request = orderRequestDto.toCatalog();
        ResponseOrderForCatalogDto responseCatalogDto = queryCatalogService.queryCatalogByProductId(request);

        if(responseCatalogDto.getProductId().startsWith("CATALOG-")){
            return false;
        }
        return true;
    }

}
