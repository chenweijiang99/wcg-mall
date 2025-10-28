package com.chenweijiang.wcg_mall.utils;

import lombok.Data;

@Data
public class JuHeLoginResponse {
    private Integer code;
    private String msg;
    private String cxid;
    private String logurl;
    private String logqrcode;
}
