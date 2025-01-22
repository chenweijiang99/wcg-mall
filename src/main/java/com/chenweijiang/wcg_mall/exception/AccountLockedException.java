package com.chenweijiang.wcg_mall.exception;

/**
 * 账号被锁定异常
 * @author 枳枳
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
