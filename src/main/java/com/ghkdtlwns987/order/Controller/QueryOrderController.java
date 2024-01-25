package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Global.ResultCode;
import com.ghkdtlwns987.order.Global.ResultListResponse;
import com.ghkdtlwns987.order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class QueryOrderController {
    private final QueryOrderService queryOrderService;

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ResultListResponse> getOrders(@PathVariable String userId) throws Exception {
        List<OrderResponseDto> result = queryOrderService.getOrderByUserId(userId);
        ResultListResponse resultResponse = ResultListResponse.of(ResultCode.GET_ORDER_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
