package com.ghkdtlwns987.order.Exception.Class;

import com.ghkdtlwns987.order.Exception.BuisinessException;
import com.ghkdtlwns987.order.Exception.ErrorCode;

public class ProductIdAlreadyExistsException extends BuisinessException {
    public ProductIdAlreadyExistsException(){
        super(ErrorCode.PRODUCT_ID_ALREADY_EXISTS);
    }
}
