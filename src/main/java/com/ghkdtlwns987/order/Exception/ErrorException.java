package com.ghkdtlwns987.order.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorException extends RuntimeException{
    private final ErrorCode errorCode;
}
