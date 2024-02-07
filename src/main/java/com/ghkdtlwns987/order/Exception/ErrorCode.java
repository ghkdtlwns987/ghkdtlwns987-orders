package com.ghkdtlwns987.order.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ORDER_ID_ALREADY_EXISTS("O001", HttpStatus.INTERNAL_SERVER_ERROR, "ORDER ID IS ALREADY EXISTS"),
    PRODUCT_ID_NOT_EXISTS("O002", HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT ID IS NOT EXISTS"),
    PRODUCT_NAME_NOT_EXISTS("O003", HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT NAME IS NOT EXISTS"),

    USERID_IS_NOT_EXISTS("O001", HttpStatus.BAD_REQUEST, "USER ID NOT EXISTS"),
    ORDER_ID_NOT_EXISTS("O001", HttpStatus.BAD_REQUEST, "ORDER ID NOT EXISTS"),
    INVALID_TYPE_VALUE("C004", HttpStatus.BAD_REQUEST, "INVALID TYPE VALUE"),
    OUT_OF_STOCK("C004", HttpStatus.BAD_REQUEST, "OUT OF PRODUCT STOCK"),

    INTERNAL_SERVER_ERROR("S005", HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");

    ;

    private final String code;
    private final HttpStatus status;
    private final String message;
}
