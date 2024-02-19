package com.chenweijiang.wcg_mall.exception;

import com.chenweijiang.wcg_mall.constant.MessageConstant;

public class AddToWishListException extends BaseException{
    public AddToWishListException()
    {
        super(MessageConstant.ADD_TO_WISH_LIST_FAILED);
    }

    public AddToWishListException(String msg)
    {
        super(msg);
    }
}
