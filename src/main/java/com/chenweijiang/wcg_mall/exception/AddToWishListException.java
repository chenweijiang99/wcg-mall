package com.chenweijiang.wcg_mall.exception;

import com.chenweijiang.wcg_mall.constant.MessageConstant;

/**
 * 添加到愿望单异常
 * @author 枳枳
 */
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
