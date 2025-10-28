package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import com.chenweijiang.wcg_mall.pojo.vo.AdminOrderDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.OrderDetailProductList;
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
    @Select("select * from order_detail where order_number = #{orderNumber}")
    List<OrderDetail> getOrderDetalByOrderNumber(String orderNumber);
}
