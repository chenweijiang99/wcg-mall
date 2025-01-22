package com.chenweijiang.wcg_mall.exception;

/**
 * 账号不存在异常
 * @author 枳枳
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}
