package com.chenweijiang.wcg_mall.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.chenweijiang.wcg_mall.properties.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AlipayUtil {
    @Autowired
    private AlipayProperties alipayProperties;

    public String pay(Alipay alipay) throws AlipayApiException{
        log.info("支付宝支付配置：{}",alipayProperties.toString());
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getGatewayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType()
        );
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        alipayTradePagePayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no",alipay.getOut_trade_no());
        //支付金额，最小值0.01元
        bizContent.put("total_amount",alipay.getTotal_amount());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", alipay.getSubject());
        bizContent.put("body", alipay.getBody());
        bizContent.put("timeout_express", alipay.getTimeout_express());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("qr_pay_mode",4);
        alipayTradePagePayRequest.setBizContent(bizContent.toJSONString());
//        alipayTradePagePayRequest.setBizContent(JSON.toJSONString(alipay));
//        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayTradePagePayRequest,"GET");
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayTradePagePayRequest,"POST");
        String pageRedirectionData = response.getBody();
        //System.out.println(pageRedirectionData);
        return pageRedirectionData;
    }
}
