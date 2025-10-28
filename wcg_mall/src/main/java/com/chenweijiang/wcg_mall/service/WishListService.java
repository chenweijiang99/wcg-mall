package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;

import java.util.List;

public interface WishListService {
    List<WishListVO> getListByUserId(Long userId);

    int delete(Long productId, Long userId);
}
