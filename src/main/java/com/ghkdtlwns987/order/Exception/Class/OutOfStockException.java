package com.ghkdtlwns987.order.Exception.Class;

import com.ghkdtlwns987.order.Exception.BuisinessException;
import com.ghkdtlwns987.order.Exception.ErrorCode;

public class OutOfStockException extends BuisinessException {
    public OutOfStockException(){
        super(ErrorCode.PRODUCT_ID_NOT_EXISTS);
    }

}
