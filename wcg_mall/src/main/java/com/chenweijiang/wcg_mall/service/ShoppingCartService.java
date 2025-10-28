package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartVO> listByUserId(Long userId);

    void addShoppingCart(Long userId, Long productId);

    void deleteShoppingCart(Long userId, Long productId);

    void reduceProduct(Long userId, Long productId);

    void deleteShoppingCartData(Long userId, Long productId);

    void addProduct(Long userId, Long productId);

    void emptyShoppingCartData(Long userId);

    void addCarProductToWishList(Long userId, Long productId);

    void deleteShoppingCartByUserIdAndProductId(Long userId, Long productId);
}
