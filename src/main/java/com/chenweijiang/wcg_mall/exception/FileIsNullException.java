package com.chenweijiang.wcg_mall.exception;

/**
 * 文件为空异常
 * @author 枳枳
 */
public class FileIsNullException extends BaseException{
    public FileIsNullException()
    {

    }

    public FileIsNullException(String msg)
    {
        super(msg);
    }
}
