package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;

import java.util.List;

public interface OrderDetailService {
    void add(OrderDetail orderDetail);

    List<OrderDetailProductList> getByOrderNumber(String orderNumber);

    List<AdminOrderDetailVO> getOrderDetail();

    List<OrderDetail> getOrderDetailByOrderNumber(String orderNumber);
}
