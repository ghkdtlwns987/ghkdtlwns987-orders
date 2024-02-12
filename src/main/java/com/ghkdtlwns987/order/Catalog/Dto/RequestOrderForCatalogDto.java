package com.ghkdtlwns987.order.Catalog.Dto;

import lombok.*;

/**
 * Order 서비스에서 Catalog 서비스에 해당하는 상품이 존재하는지 확인하는 Dto 입니다.
 * 이 때 ProductId를 기반으로 Catalog 서비스와 통신합니다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderForCatalogDto {
    /* 상품 Id */
    private String productId;

    /* 상품 명 */
    private String productName;

    /* 주문 수량 */
    private Integer qty;

    /* 가격 */
    private Integer unitPrice;
}