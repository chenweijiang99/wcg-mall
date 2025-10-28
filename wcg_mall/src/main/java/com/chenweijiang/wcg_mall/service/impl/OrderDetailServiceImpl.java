package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.OrderDetailMapper;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailMapper orderDetailMapper;

    @Override
    public void add(OrderDetail orderDetail) {
        orderDetailMapper.add(orderDetail);
    }

    @Override
    public List<OrderDetailProductList> getByOrderNumber(String orderNumber) {
        return orderDetailMapper.getByOrderNumber(orderNumber);
    }

    @Override
    public List<AdminOrderDetailVO> getOrderDetail() {
        return orderDetailMapper.getOrderDetailList();
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderNumber(String orderNumber) {

        return orderDetailMapper.getOrderDetalByOrderNumber(orderNumber);
    }
}
