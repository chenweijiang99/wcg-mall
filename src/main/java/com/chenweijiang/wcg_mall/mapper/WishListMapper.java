package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.UserWishList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WishListMapper {
    @Select("select * from user_wish_list where user_id = #{userId} order by id")
    List<UserWishList> getListByUserId(Long userId);

    @Delete("delete from user_wish_list where user_id = #{userId} and product_id = #{productId}")
    int delete(Long productId, Long userId);
}
