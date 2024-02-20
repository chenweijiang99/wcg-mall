package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.ShoppingCart;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartVO> listByUserId(Long userId);

    void addShoppingCart(Long userId, Long productId);

    void deleteShoppingCart(Long userId, Long productId);
}
