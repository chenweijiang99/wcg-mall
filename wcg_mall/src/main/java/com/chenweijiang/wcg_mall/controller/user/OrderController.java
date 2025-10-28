package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户订单接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/order")
@Slf4j
@Tag(name = "用户订单相关接口")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    private final OrderDetailService orderDetailService;
    
    private final UserService userService;
    
    private final ShoppingCartService shoppingCartService;
    
    private final ProductService productService;
    
    private final StringRedisTemplate stringRedisTemplate;
    
    private final RedisTemplate redisTemplate;

    //用户确认收货
    @PutMapping("/confirmReceipt/{orderNumber}")
    @Operation(summary = "用户确认收货")
    public Result confirmReceipt(@PathVariable String orderNumber) {
        log.info("用户确认收货{}",orderNumber);
        Order order = orderService.getByOrderNumber(orderNumber);
        order.setStatus(Order.COMPLETED);
        orderService.update(order);
        return Result.success();
    }

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
        log.info("生成订单{}",orderDTO);
        //创建订单
        Order order = Order.builder()
                .consignee(orderDTO.getConsignee())
                .consigneeAddress(orderDTO.getConsigneeAddress())
                .consigneePhone(orderDTO.getConsigneePhone())
                .amount(orderDTO.getAmount())
                .payMethod(orderDTO.getPayMethod())
                .build();
        //获取用户数据
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        //填充用户信息到订单
        order.setUserId(userId);
        order.setOrderNumber(System.currentTimeMillis()+userId.toString());
        order.setStatus(Order.PENDING);
        order.setPayStatus(Order.UN_PAID);
        order.setEmail(user.getEmail());
        order.setOrderTime(LocalDateTime.now());
        //将购物车数据添加到订单详情
        orderDTO.getShoppingCartList().forEach(cartVO -> {
            Product product = productService.getProductById(cartVO.getProductId());
            if(product.getInventory() <= cartVO.getNumber()){
                throw new RuntimeException("库存不足");
            }
            product.setInventory(product.getInventory() - cartVO.getNumber());
            productService.updateProduct(product);
            redisTemplate.delete(RedisConstant.PRODUCT_LIST);
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderNumber(order.getOrderNumber())
                    .productId(cartVO.getProductId())
                    .productNumber(cartVO.getNumber())
                    .build();
            orderDetailService.add(orderDetail);
            //删除购物车数据
            shoppingCartService.deleteShoppingCartByUserIdAndProductId(userId, cartVO.getProductId());
        });

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

    //用户取消订单
    @PutMapping("/cancelOrder/{orderNumber}")
    @Operation(summary = "用户取消订单")
    public Result<String> cancelOrder(@PathVariable String orderNumber) {
        log.info("用户取消订单{}",orderNumber);
        Order order = orderService.getByOrderNumber(orderNumber);
        order.setStatus(Order.CANCELED);
        orderService.update(order);
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderNumber(orderNumber);
        orderDetailList.forEach(orderDetail -> {
            Product product = productService.getProductById(orderDetail.getProductId());
            product.setInventory(product.getInventory() + orderDetail.getProductNumber());
            productService.updateProduct(product);
            redisTemplate.delete(RedisConstant.PRODUCT_LIST);
        });
        return Result.success(MessageConstant.SUCCESS);
    }
}

