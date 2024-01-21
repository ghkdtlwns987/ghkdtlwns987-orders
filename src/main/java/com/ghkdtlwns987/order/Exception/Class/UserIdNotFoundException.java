package com.ghkdtlwns987.order.Exception.Class;

import com.ghkdtlwns987.order.Exception.BuisinessException;
import com.ghkdtlwns987.order.Exception.ErrorCode;

public class UserIdNotFoundException extends BuisinessException {
    public UserIdNotFoundException(){
        super(ErrorCode.USERID_IS_NOT_EXISTS);
    }
}
