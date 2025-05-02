package com.chenweijiang.wcg_mall.controller.user;

//import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.properties.AliOssProperties;
import com.chenweijiang.wcg_mall.properties.AlipayProperties;
import com.chenweijiang.wcg_mall.utils.Alipay;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.OrderDetailService;
import com.chenweijiang.wcg_mall.service.OrderService;
import com.chenweijiang.wcg_mall.service.ProductService;
import com.chenweijiang.wcg_mall.service.ShoppingCartService;
import com.chenweijiang.wcg_mall.utils.AlipayUtil;
import com.chenweijiang.wcg_mall.websocket.WebSocketServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;

/**
 * 用户支付宝相关接口
 * @author 枳枳
 */
@RestController("userAlipayController")
@Slf4j
@Tag(name = "用户支付宝相关接口")
@RequestMapping("/user/alipay")
@RequiredArgsConstructor // Lombok注解，生成构造函数
public class AlipayController {

    private final AlipayUtil alipayUtil;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;
    private final WebSocketServer webSocketServer;
    private final AlipayProperties  aliPayProperties;
    @GetMapping()
    @Operation(summary = "支付宝支付")
    public Result<String> alipayPay(String orderNumber) throws Exception {
        log.info("订单支付{}",orderNumber);
        // 支付账号：  oworsb4854@sandbox.com    密码：111111
        Order order = orderService.getByOrderNumber(orderNumber);
        String alipayPay = alipayUtil.pay(order);
        return Result.success(alipayPay);
    }



    @PostMapping("/notify")  // 注意这里必须是POST接口
    @Operation(summary = "支付宝支付回调")
    public Result<String> payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            log.info("支付宝异步回调");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayProperties.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("支付宝异步回调验签成功");
                log.info("订单号：{}", params.get("out_trade_no"));
                log.info("交易状态：{}", params.get("trade_status"));
                log.info("交易金额:{} " , params.get("total_amount"));
                String tradeNo = params.get("out_trade_no");
                // 更新订单状态为已支付，设置支付信息
                Order order = orderService.getByOrderNumber(tradeNo);
                if(order == null){
                    return Result.error("订单不存在");
                }
                List<OrderDetailProductList> orderDetailProductListList = orderDetailService.getByOrderNumber(tradeNo);
                if(orderDetailProductListList == null){
                    return Result.error("订单详情不存在");
                }
                orderDetailProductListList.forEach(orderDetailProductList -> {
                    //减少库存
                    productService.decreaseProductInventory(orderDetailProductList.getId(), orderDetailProductList.getProductNumber());
                });
                order.setPayStatus(Order.PAID);// 支付状态改为已支付
                order.setStatus(Order.UN_DELIVERED);// 订单状态改为待发货
                order.setCheckoutTime(LocalDateTime.now());
                orderService.update(order);

                log.info("发送支付成功消息到前端");
                webSocketServer.sendToAllClient(tradeNo);// 发送支付成功消息到前端
            }

        }
        return Result.success();
    }

    @PutMapping("/refund")
    @Operation(summary = "支付宝退款")
    public Result<String> refund(String orderNumber) throws AlipayApiException {
        Order order = orderService.getByOrderNumber(orderNumber);
        if (ObjectUtil.isNull(order)) {
            return Result.error("订单不存在");
        }
        //调用支付宝退款接口
        boolean isRefund = alipayUtil.refund(order);
        if (isRefund) {
            log.info("订单号{}退款成功",orderNumber);
            Order dbOrder = orderService.getByOrderNumber(orderNumber);
            if(dbOrder == null){
                return Result.error("订单不存在");
            }
            dbOrder.setStatus(Order.REFUNDED);// 订单状态改为已退款
            dbOrder.setPayStatus(Order.REFUND);// 支付状态改为已退款
            orderService.update(dbOrder);

            List<OrderDetailProductList> orderDetailProductListList = orderDetailService.getByOrderNumber(orderNumber);
            if(orderDetailProductListList == null){
                return Result.error("订单详情不存在");
            }
            orderDetailProductListList.forEach(orderDetailProductList -> {
                // 增加库存
                productService.increaseProductInventory(orderDetailProductList.getId(), orderDetailProductList.getProductNumber());
            });
        }else {
            log.error("订单号{}退款失败",orderNumber);
            return Result.error("退款失败");
        }

        return Result.success();
    }


}
