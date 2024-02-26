package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {

    @Insert("insert into order_detail (order_number, product_id, product_number) values ( #{orderNumber},#{productId}, #{productNumber})")
    void add(OrderDetail orderDetail);
}
