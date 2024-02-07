package com.ghkdtlwns987.order.Dto;

import com.ghkdtlwns987.order.Catalog.Dto.RequestOrderForCatalogDto;
import com.ghkdtlwns987.order.Entity.Order;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    /* 상품 Id */
    private String productId;

    /* 남은 수량 */
    private Integer qty;

    /* 개당 가격 */
    private Integer unitPrice;


    public Order toEntity(String userId){
        return Order.builder()
                .productId(productId)
                .qty(qty)
                .unitPrice(unitPrice)
                .totalPrice(qty * unitPrice)
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .build();
    }

    public RequestOrderForCatalogDto toCatalog(){
        return RequestOrderForCatalogDto
                .builder()
                .productId(productId)
                .build();
    }
}
