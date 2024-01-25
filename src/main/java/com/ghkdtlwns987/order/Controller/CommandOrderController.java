package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Global.ResultCode;
import com.ghkdtlwns987.order.Global.ResultListResponse;
import com.ghkdtlwns987.order.Global.ResultResponse;
import com.ghkdtlwns987.order.Service.Inter.CommandOrderService;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class CommandOrderController
{
    private final QueryOrderService queryOrderService;
    private final CommandOrderService commandOrderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResultResponse> createOrder(@PathVariable String userId, @RequestBody OrderRequestDto request) throws Exception {
        OrderResponseDto result = commandOrderService.createOrder(userId, request);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.ORDER_REQUEST_SUCCESS, result);

        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ResultListResponse> getOrders(@PathVariable String userId) throws Exception {
        List<OrderResponseDto> result = queryOrderService.getOrderByUserId(userId);
        ResultListResponse resultResponse = ResultListResponse.of(ResultCode.GET_ORDER_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
