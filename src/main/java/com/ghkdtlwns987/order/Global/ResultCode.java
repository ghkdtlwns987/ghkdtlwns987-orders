package com.ghkdtlwns987.order.Global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Order
    ORDER_REQUEST_SUCCESS(200, "O001", "회원 주문이 접수되었습니다."),
    GET_ORDER_REQUEST_SUCCESS(200, "O001", "주문 내역을 조회했습니다."),

    GET_MEMBER_ORDER_REQUEST_SUCCESS(200, "O002", "회원 주문이 조회 되었습니다."),
    GET_ALL_CATALOG_REQUEST_SUCCESS(200, "C001", "전체 상품 조회가 완료되었습니다."),
    ;
    private int status;
    private final String code;
    private final String message;
}