package com.chenweijiang.wcg_mall.exception;

/**
 * 用户未登录异常
 * @author 枳枳
 */
public class UserNotLoginException extends BaseException {

    public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }

}
