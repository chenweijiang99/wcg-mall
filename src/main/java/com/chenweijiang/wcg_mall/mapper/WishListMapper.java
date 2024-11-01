package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WishListMapper {
    @Select("select u.id as id,user_id,product_id,name as product_name,price as productPrice,image as productImage from user_wish_list as u,product as p where user_id = #{userId} and u.product_id = p.id order by id")
    List<WishListVO> getListByUserId(Long userId);

    @Delete("delete from user_wish_list where user_id = #{userId} and product_id = #{productId}")
    int delete(Long productId, Long userId);
    @Insert("insert into user_wish_list(user_id,product_id) values (#{userId},#{productId})")
    int addToWishList(Long userId, Long productId);
}
