package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Tag(name = "用户订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "获取订单数据")
    public Result<List<Order>> listByUserId() {
        log.info("获取用户订单数据{}");
        Long userId = BaseContext.getCurrentId();
        List<Order> orderList = orderService.list(userId);
        return Result.success(orderList);
    }


}

