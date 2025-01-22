package com.chenweijiang.wcg_mall.exception;

/**
 * 愿望单已存在异常
 * @author 枳枳
 */
public class WishListAlreadyExistsException extends BaseException{
    public WishListAlreadyExistsException()
    {
    }

    public WishListAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
