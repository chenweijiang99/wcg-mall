package com.chenweijiang.wcg_mall.service;

import com.alipay.api.domain.OrderVO;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailVO;

import java.util.List;

public interface OrderDetailService {
    void add(OrderDetail orderDetail);

    List<OrderDetailProductList> getByOrderNumber(String orderNumber);

    List<AdminOrderDetailVO> getOrderDetail();
}
