package com.chenweijiang.wcg_mall.exception;

public class WishListAlreadyExistsException extends BaseException{
    public WishListAlreadyExistsException()
    {
    }

    public WishListAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
