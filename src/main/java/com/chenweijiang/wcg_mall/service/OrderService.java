package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> list(Long userId);
}
