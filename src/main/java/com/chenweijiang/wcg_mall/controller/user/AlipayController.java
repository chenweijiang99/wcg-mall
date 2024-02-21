package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.pojo.Alipay;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.utils.AlipayUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController("userAlipayController")
@Slf4j
@Tag(name = "用户支付宝相关接口")
@RequestMapping("/user/alipay")
public class AlipayController {

    @Autowired
    private AlipayUtil alipayUtil;

    @GetMapping("")
    @Operation(summary = "支付宝支付")
    public String alipayPay(Long id) throws Exception {
        log.info("支付宝支付");
        // 支付账号：  oworsb4854@sandbox.com    密码：111111
        Alipay alipay = Alipay.builder()
                .out_trade_no(LocalDateTime.now().toString())
                .subject("test")
                .total_amount("100")
                .body("test")
                .build();
        return alipayUtil.pay(alipay);
    }

    @GetMapping("/success")
    @Operation(summary = "支付宝支付回调")
    public String alipayNotify() throws Exception {
        log.info("支付宝支付回调");
        return "success";
    }
}
