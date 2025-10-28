package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.WishListMapper;
import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;
import com.chenweijiang.wcg_mall.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {


    private final WishListMapper wishListMapper;

    @Override
    public List<WishListVO> getListByUserId(Long userId) {
        List<WishListVO> userWishList = wishListMapper.getListByUserId(userId);
        return userWishList;
    }

    @Override
    public int delete(Long productId, Long userId) {
        if(wishListMapper.delete(productId,userId) > 0){
            return 1;
        }
        return 0;
    }
}
