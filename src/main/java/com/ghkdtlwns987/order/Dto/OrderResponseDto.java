package com.ghkdtlwns987.order.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghkdtlwns987.order.Entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String userId;
    private String orderId;

    private LocalDateTime orderedAt;

    public static OrderResponseDto fromEntity(Order order){
        return new OrderResponseDto(
                order.getProductId(),
                order.getQty(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getUserId(),
                order.getOrderId(),
                order.getOrderAt()
        );
    }
}
