package com.ghkdtlwns987.order.Exception;

import org.springframework.http.HttpStatus;

public class ClientException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus responseStatus;
    private final String displayErrorMessage;

    public ClientException(ErrorCode errorCode, String messageForLog) {
        this(errorCode, errorCode.getMessage(), messageForLog);
    }

    public ClientException(ErrorCode errorCode, String displayErrorMessage, String messageForLog) {
        super(messageForLog);
        this.errorCode = errorCode;
        this.responseStatus = errorCode.getStatus();
        this.displayErrorMessage = displayErrorMessage;
    }

    public String getDisplayErrorMessage() {
        return this.displayErrorMessage;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public HttpStatus getResponseStatus() {
        return this.responseStatus;
    }
}
