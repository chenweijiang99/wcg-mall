package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.OrderMapper;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    @Override
    public List<Order> list(Long userId) {
        return orderMapper.listByUserId(userId);
    }

    @Override
    public void save(Order order) {
        orderMapper.save(order);
    }

    @Override
    public Order getByOrderNumber(String orderNumber) {
        return orderMapper.getByOrderNumber(orderNumber);
    }

    @Override
    public void update(Order order) {
         orderMapper.update(order);
    }

    @Override
    public List<Order> getOrderList() {
        return orderMapper.getOrderList();
    }

    @Override
    public List<Order>  getTimeoutOrder() {
        return  orderMapper.getTimeoutOrder();
    }


}
