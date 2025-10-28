package com.chenweijiang.wcg_mall.exception;

import com.chenweijiang.wcg_mall.constant.MessageConstant;

/**
 * 账户已存在异常
 * @author 枳枳
 */
public class AccountAlreadyExistsException extends BaseException{
    public AccountAlreadyExistsException()
    {

    }
    public AccountAlreadyExistsException(String msg)
    {
        super(msg);
    }

}
