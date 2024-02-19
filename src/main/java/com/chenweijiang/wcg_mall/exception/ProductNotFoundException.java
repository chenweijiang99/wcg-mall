package com.chenweijiang.wcg_mall.exception;

public class ProductNotFoundException extends BaseException{
    public ProductNotFoundException(){}

    public ProductNotFoundException(String msg)
    {
        super(msg);
    }
}
