package com.ghkdtlwns987.order.Controller;

import com.ghkdtlwns987.order.Dto.OrderRequestDto;
import com.ghkdtlwns987.order.Dto.OrderResponseDto;
import com.ghkdtlwns987.order.Service.Inter.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable String userId, @RequestBody OrderRequestDto request){
        OrderResponseDto response = orderService.createOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable String userId){
        List<OrderResponseDto> response = orderService.getOrderByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
