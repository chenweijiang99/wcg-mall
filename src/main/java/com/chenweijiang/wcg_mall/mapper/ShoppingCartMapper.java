package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.ShoppingCart;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCartVO> listByUserId(Long userId);

    @Select("select * from shopping_cart where user_id = #{userId} and product_id = #{productId}")
    ShoppingCart getShoppingCartByUserIdAndProductId(Long userId, Long productId);
    @Insert("insert into shopping_cart (user_id, product_id, number) values (#{userId}, #{productId}, #{number})")
    void addShoppingCart(ShoppingCart addShoppingCart);

    void updateShoppingCart(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteShoppingCart(Long id);

    @Delete("delete from shopping_cart where user_id = #{userId} ")
    void deleteShoppingCartByUserId(Long userId);
    @Delete("delete from shopping_cart where user_id = #{userId} and product_id = #{productId}")
    void deleteShoppingCartByUserIDAndProductID(Long userId, Long productId);
}
