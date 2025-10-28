package com.chenweijiang.wcg_mall.utils;

import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.service.OrderDetailService;
import com.chenweijiang.wcg_mall.service.OrderService;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 枳枳
 */
@Slf4j
@Tag(name = "定时任务工具类")
@Component
public class ScheduleTaskUtil   {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;
    // 定时15分钟执行一次
    @Scheduled(cron="0 0/5 * * * ?")
    public void task() {
        log.info("定时取消超时订单");
        List<Order> orderList =orderService.getTimeoutOrder();
        orderList.forEach(order -> {
            order.setStatus(Order.CANCELED);
            orderService.update(order);
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderNumber(order.getOrderNumber());
            orderDetailList.forEach(orderDetail -> {
                Product product = productService.getProductById(orderDetail.getProductId());
                product.setInventory(product.getInventory() + orderDetail.getProductNumber());
                productService.updateProduct(product);
//                redisTemplate.delete(RedisConstant.PRODUCT_LIST);
            });
        });
    }
}
