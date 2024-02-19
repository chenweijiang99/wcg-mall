package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.UserWishList;

import java.util.List;

public interface WishListService {
    List<UserWishList> getListByUserId(Long userId);

    int delete(Long productId, Long userId);
}
