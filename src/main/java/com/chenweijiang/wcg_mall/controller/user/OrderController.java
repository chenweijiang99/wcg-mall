package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.dto.OrderDTO;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Tag(name = "用户订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;
    @GetMapping
    @Operation(summary = "获取订单数据")
    public Result<List<Order>> listByUserId() {
        log.info("获取用户订单数据{}");
        Long userId = BaseContext.getCurrentId();
        List<Order> orderList = orderService.list(userId);
        return Result.success(orderList);
    }

    @GetMapping("/getOrderStatus")
    @Operation(summary = "获取订单是否支付")
    public Result<String> getOrderStatus(String orderNumber) {
        log.info("获取订单是否支付{}",orderNumber);
        Order order = orderService.getByOrderNumber(orderNumber);
        if(order == null){
            return Result.error(MessageConstant.ORDER_NOT_FOUND);
        }
       if(order.getPayStatus() == Order.PAID){
           return Result.success(MessageConstant.ORDER_PAID);
       }
        return Result.error(MessageConstant.ORDER_NOT_PAID);
    }


    @PostMapping
    @Operation(summary = "生成订单")
    public Result<Order> createOrder(@RequestBody OrderDTO orderDTO) {

        Order order = Order.builder()
                .consignee(orderDTO.getConsignee())
                .consigneeAddress(orderDTO.getConsigneeAddress())
                .consigneePhone(orderDTO.getConsigneePhone())
                .amount(orderDTO.getAmount())
                .payMethod(orderDTO.getPayMethod())
                .build();
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        order.setUserId(userId);
        order.setOrderNumber(LocalDateTime.now().toString());
        order.setStatus(Order.PENDING);
        order.setPayStatus(Order.UN_PAID);
        order.setEmail(user.getEmail());
        order.setOrderTime(LocalDateTime.now());
        log.info("生成订单{}",order);
        //将购物车数据添加到订单详情
        List<ShoppingCartVO> cartVOS = shoppingCartService.listByUserId(userId);
        cartVOS.forEach(cartVO -> {
            Product product = productService.getProductById(cartVO.getProductId());
            if(product.getInventory() <= cartVO.getNumber()){
                return ;
            }
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderNumber(order.getOrderNumber())
                    .productId(cartVO.getProductId())
                    .productNumber(cartVO.getNumber())
                    .build();
            orderDetailService.add(orderDetail);
        });
        //清空购物车数据
        shoppingCartService.emptyShoppingCartData(userId);
        //生成订单
        orderService.save(order);

        return Result.success(order);
    }


    @GetMapping("/getOrderDetail")
    @Operation(summary = "获取订单详情")
    public Result<OrderDetailVO> getOrderDetail(String orderNumber) {
        log.info("获取订单详情{}",orderNumber);
        Order order = orderService.getByOrderNumber(orderNumber);
        List<OrderDetailProductList> OrderDetailProductList = orderDetailService.getByOrderNumber(orderNumber);
        OrderDetailVO orderDetailVO = OrderDetailVO.builder()
                .orderNumber(orderNumber)
                .status(order.getStatus())
                .consignee(order.getConsignee())
                .consigneeAddress(order.getConsigneeAddress())
                .consigneePhone(order.getConsigneePhone())
                .email(order.getEmail())
                .payMethod(order.getPayMethod())
                .payStatus(order.getPayStatus())
                .amount(order.getAmount())
                .orderTime(order.getOrderTime())
                .checkoutTime(order.getCheckoutTime())
                .productLists(OrderDetailProductList)
                .build();
        return Result.success(orderDetailVO);
    }
}

