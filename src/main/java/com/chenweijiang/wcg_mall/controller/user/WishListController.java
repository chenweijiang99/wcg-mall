package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.UserWishList;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/wishList")
@Tag(name = "心愿单相关接口")
@Slf4j
public class WishListController {

    @Autowired
    private WishListService wishListService;


    @GetMapping
    @Operation(summary = "获取心愿单")
    public Result<List<UserWishList>> getWishList() {
        log.info("获取心愿单");
        Long userId = BaseContext.getCurrentId();
        List<UserWishList> wishList = wishListService.getListByUserId(userId);
        if (wishList == null || wishList.size() == 0) {
            return Result.error(MessageConstant.WISH_LIST_IS_NULL);
        }
        return Result.success(wishList);
    }

    @DeleteMapping
    @Operation(summary = "删除心愿单")
    public Result<String> deleteWishList(Long productId) {
        log.info("删除心愿单");
        Long userId = BaseContext.getCurrentId();
        int result = wishListService.delete(productId,userId);
        if (result == 0) {
            return Result.error(MessageConstant.WISH_LIST_DELETE_FAILED);
        }
        return Result.success(MessageConstant.WISH_LIST_DELETE_SUCCESS);
    }
}
