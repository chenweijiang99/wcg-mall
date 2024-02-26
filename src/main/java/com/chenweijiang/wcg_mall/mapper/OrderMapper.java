package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from `order` where user_id = #{userId}")
    List<Order> listByUserId(Long userId);

    void save(Order order);

    @Select("select * from `order` where order_number = #{orderNumber}")
    Order getByOrderNumber(String orderNumber);

    void update(Order order);
}
