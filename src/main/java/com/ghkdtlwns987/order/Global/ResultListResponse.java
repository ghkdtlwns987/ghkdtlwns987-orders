package com.ghkdtlwns987.order.Global;

import lombok.Getter;

import java.util.List;

@Getter
public class ResultListResponse<T> {
    private int status;
    private String code;
    private String message;
    private List<T> data;

    public static <T> ResultListResponse<T> of(ResultCode resultCode, List<T> data) {
        return new ResultListResponse<>(resultCode, data);
    }

    public ResultListResponse(ResultCode resultCode, List<T> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
}
