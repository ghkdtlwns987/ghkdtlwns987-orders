package com.ghkdtlwns987.order.Exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BuisinessException extends RuntimeException{
    private ErrorCode errorCode;
    private List<ErrorResponse.FieldError> errors = new ArrayList<>();

    public BuisinessException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BuisinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BuisinessException(ErrorCode errorCode, List<ErrorResponse.FieldError> errors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
