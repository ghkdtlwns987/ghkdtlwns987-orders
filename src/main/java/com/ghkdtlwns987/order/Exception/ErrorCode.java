package com.ghkdtlwns987.order.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ORDER_ID_ALREADY_EXISTS("O001", 500, "ORDER ID IS ALREADY EXISTS"),
    PRODUCT_ID_ALREADY_EXISTS("O002", 500, "PRODUCT ID IS ALREADY EXISTS"),
    PRODUCT_ID_NOT_EXISTS("O002", 500, "PRODUCT ID IS NOT EXISTS"),

    USERID_IS_NOT_EXISTS("O001", 400, "USER ID NOT EXISTS"),
    ORDER_ID_NOT_EXISTS("O001", 400, "ORDER ID NOT EXISTS"),
    INVALID_TYPE_VALUE("C004", 400, "INVALID TYPE VALUE"),
    ;

    private final String code;
    private final int status;
    private final String message;
}
