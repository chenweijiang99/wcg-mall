package com.chenweijiang.wcg_mall.utils;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.properties.AlipayProperties;
import com.chenweijiang.wcg_mall.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 支付宝支付工具类
 * @author 枳枳
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AlipayUtil {

    private final AlipayProperties alipayProperties;

    public String pay(Order order) throws AlipayApiException{
        log.info("调用支付宝支付接口：{}",alipayProperties.toString());
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
        bizContent.put("out_trade_no",order.getOrderNumber());
        //支付金额，最小值0.01元
        bizContent.put("total_amount",order.getAmount());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", order.getOrderNumber());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //二维码模式，固定传值4，扫码支付模式一（付款码）
        bizContent.put("qr_pay_mode",4);
        alipayTradePagePayRequest.setBizContent(bizContent.toJSONString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayTradePagePayRequest,"POST");
        return response.getBody();
    }

    public boolean refund(Order order) throws AlipayApiException{
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getGatewayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType()
        );

        // 2. 创建 Request并设置Request参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        cn.hutool.json.JSONObject bizContent = new cn.hutool.json.JSONObject();
        bizContent.set("out_trade_no", order.getOrderNumber());  // 订单编号  必须是不重复的退款订单号
        bizContent.set("refund_amount", order.getAmount()); // 订单的总金额
        bizContent.set("out_request_no", IdUtil.fastSimpleUUID());   // 随机数
        request.setBizContent(bizContent.toString());
        try {
            // 退款调用接口
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            return response.isSuccess();
        } catch (AlipayApiException e) {
            return false;
        }
    }


}
