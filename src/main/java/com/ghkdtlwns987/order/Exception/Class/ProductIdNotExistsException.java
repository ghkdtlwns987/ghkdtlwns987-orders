package com.ghkdtlwns987.order.Exception.Class;

import com.ghkdtlwns987.order.Exception.BuisinessException;
import com.ghkdtlwns987.order.Exception.ErrorCode;

public class ProductIdNotExistsException extends BuisinessException {
    public ProductIdNotExistsException(){
        super(ErrorCode.PRODUCT_ID_NOT_EXISTS);
    }
}
