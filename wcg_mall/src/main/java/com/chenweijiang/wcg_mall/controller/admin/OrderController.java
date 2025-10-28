package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.OrderDetailService;
import com.chenweijiang.wcg_mall.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员订单相关接口
 * @author 枳枳
 */
@RestController("adminOrderController")
@Slf4j
@RequestMapping("/admin/order")
@Tag(name = "管理订单相关接口")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    private final OrderDetailService orderDetailService;

    @GetMapping
    @Operation(summary = "订单列表")
    public Result<List<Order>> getOrderList(){
        log.info("获取订单列表");
        List<Order> orderList = orderService.getOrderList();
        return Result.success(orderList);
    }
    @GetMapping("/getOrderDetailList")
    @Operation(summary = "订单详情列表")
    public Result<List<AdminOrderDetailVO>> getOrderDetail(){
        log.info("获取订单详情列表");
        List<AdminOrderDetailVO> orderVOList = orderDetailService.getOrderDetail();
        return Result.success(orderVOList);
    }

    @PostMapping("/OrderShipping")
    @Operation(summary = "订单发货")
    public Result<String> OrderShipping(String orderNumber){
        log.info("订单发货{}",orderNumber);
        Order order = orderService.getByOrderNumber(orderNumber);
        order.setStatus(Order.DELIVERED);
        orderService.update(order);
        return Result.success();
    }
}
