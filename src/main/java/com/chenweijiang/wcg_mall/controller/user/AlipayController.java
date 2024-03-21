package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.Alipay;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.Thread.sleep;

@RestController("userAlipayController")
@Slf4j
@Tag(name = "用户支付宝相关接口")
@RequestMapping("/user/alipay")
public class AlipayController {

    @Autowired
    private AlipayUtil alipayUtil;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @GetMapping()
    @Operation(summary = "支付宝支付")
    public Result<String> alipayPay(String orderNumber) throws Exception {
        log.info("支付宝支付{}",orderNumber);
        // 支付账号：  oworsb4854@sandbox.com    密码：111111
        Long userId = BaseContext.getCurrentId();
        Order order = orderService.getByOrderNumber(orderNumber);
        Alipay alipay = Alipay.builder()
                .out_trade_no(order.getOrderNumber())
                .subject(order.getOrderNumber()+"付款")
                .total_amount(String.valueOf(order.getAmount()))
                .body(order.getOrderNumber()+"付款")
                .build();
        String alipayPay = alipayUtil.pay(alipay);
        return Result.success(alipayPay);
    }


    /**
     * 这里其实是支付宝的回调接口，这里只是测试用。。。。
     * 因为没有进行内外穿透，所以这里只能用本地模拟
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    @PostMapping("/success")
    @Operation(summary = "支付宝支付回调")
    public Result<String> alipayNotify(String out_trade_no) throws Exception {
        log.info("支付宝支付回调{}",out_trade_no);
        Order order = orderService.getByOrderNumber(out_trade_no);
        if(order == null){
            return Result.error("订单不存在");
        }
        List<OrderDetailProductList> orderDetailProductListList = orderDetailService.getByOrderNumber(out_trade_no);
        if(orderDetailProductListList == null){
            return Result.error("订单详情不存在");
        }
        orderDetailProductListList.forEach(orderDetailProductList -> {
            productService.updateProductInventory(orderDetailProductList.getId(), orderDetailProductList.getProductNumber());
        });
        order.setPayStatus(Order.PAID);
        order.setStatus(Order.UN_DELIVERED);
        order.setCheckoutTime(LocalDateTime.now());
        orderService.update(order);

//        log.info("发送支付成功消息到前端");
//        webSocketServer.sendToAllClient(out_trade_no);
        return Result.success("支付成功:\n" +
                "订单号为:"+out_trade_no);
    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void sendMessageToClient() {
//        webSocketServer.sendToAllClient("这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
//    }
}
