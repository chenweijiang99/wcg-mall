package com.chenweijiang.wcg_mall.mapper;

import com.alipay.api.domain.OrderVO;
import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Insert("insert into order_detail (order_number, product_id, product_number) values ( #{orderNumber},#{productId}, #{productNumber})")
    void add(OrderDetail orderDetail);

    List<OrderDetailProductList> getByOrderNumber(String orderNumber);

    List<AdminOrderDetailVO> getOrderDetailList();
}
