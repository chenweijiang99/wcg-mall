package com.chenweijiang.wcg_mall.exception;

import com.chenweijiang.wcg_mall.constant.MessageConstant;

public class AccountAlreadyExistsException extends BaseException{
    public AccountAlreadyExistsException()
    {

    }
    public AccountAlreadyExistsException(String msg)
    {
        super(msg);
    }

}
