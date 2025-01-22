package com.chenweijiang.wcg_mall.exception;

/**
 * 登录失败异常
 * @author 枳枳
 */
public class LoginFailedException extends BaseException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
