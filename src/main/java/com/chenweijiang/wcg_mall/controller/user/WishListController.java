package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ShoppingCartService;
import com.chenweijiang.wcg_mall.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户心愿单接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/wishList")
@Tag(name = "心愿单相关接口")
@Slf4j
@RequiredArgsConstructor
public class WishListController {


    private final WishListService wishListService;

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "获取心愿单")
    public Result<List<WishListVO>> getWishList() {
        log.info("获取心愿单");
        Long userId = BaseContext.getCurrentId();
        List<WishListVO> wishList = wishListService.getListByUserId(userId);
        if (wishList == null || wishList.size() == 0) {
            return Result.error(MessageConstant.WISH_LIST_IS_NULL);
        }
        return Result.success(wishList);
    }

    @DeleteMapping
    @Operation(summary = "删除心愿单")
    public Result<String> deleteWishList(Long productId) {
        log.info("删除心愿单{}",productId);
        Long userId = BaseContext.getCurrentId();
        int result = wishListService.delete(productId,userId);
        if (result == 0) {
            return Result.error(MessageConstant.WISH_LIST_DELETE_FAILED);
        }
        return Result.success(MessageConstant.WISH_LIST_DELETE_SUCCESS);
    }

    @PostMapping
    @Operation(summary = "添加心愿单商品到购物车")
    public Result<String> addWishListProductToCart(Long productId) {
        log.info("添加心愿单商品到购物车{}",productId);
        Long userId = BaseContext.getCurrentId();
        int result = wishListService.delete(productId,userId);
        if (result == 0) {
            return Result.error(MessageConstant.ADD_PRODUCT_TO_CART_FAIL);
        }
        shoppingCartService.addShoppingCart(userId,productId);
        return Result.success(MessageConstant.ADD_PRODUCT_TO_CART_SUCCESS);
    }
}
