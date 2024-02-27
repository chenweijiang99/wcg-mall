package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.OrderDetailMapper;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public void add(OrderDetail orderDetail) {
        orderDetailMapper.add(orderDetail);
    }

    @Override
    public List<OrderDetailProductList> getByOrderNumber(String orderNumber) {
        return orderDetailMapper.getByOrderNumber(orderNumber);
    }
}
