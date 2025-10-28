package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> list(Long userId);

    void save(Order order);


    Order getByOrderNumber(String orderNumber);

    void update(Order order);

    List<Order> getOrderList();


    List<Order>  getTimeoutOrder();
}
