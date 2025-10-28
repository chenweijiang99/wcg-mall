package com.chenweijiang.wcg_mall.exception;

/**
 * 删除不允许的异常
 * @author 枳枳
 */
public class DeletionNotAllowedException extends BaseException {

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }

}
