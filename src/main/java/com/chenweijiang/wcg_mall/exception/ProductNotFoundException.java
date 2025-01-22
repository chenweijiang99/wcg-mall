package com.chenweijiang.wcg_mall.exception;

/**
 * 商品不存在异常
 * @author 枳枳
 */
public class ProductNotFoundException extends BaseException{
    public ProductNotFoundException(){}

    public ProductNotFoundException(String msg)
    {
        super(msg);
    }
}
