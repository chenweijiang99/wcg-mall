package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.ShoppingCart;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "购物车接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "获取购物车数据")
    public Result<List<ShoppingCartVO>> listByUserId(){
        log.info("获取购物车数据");
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCartVO> shoppingCartList = shoppingCartService.listByUserId(userId);
        if(shoppingCartList == null || shoppingCartList.size() == 0){
            return Result.error(MessageConstant.SHOPPING_CART_IS_NULL);
        }else{
            return Result.success(shoppingCartList);
        }
    }

    @PostMapping
    @Operation(summary = "添加购物车数据")
    public Result<String> addShoppingCart(Long productId){
        log.info("添加商品到购物车{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.addShoppingCart(userId,productId);
        return Result.success(MessageConstant.ADD_PRODUCT_TO_CART_SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "删除购物车数据")
    public Result<String> deleteShoppingCart(Long productId){
        log.info("删除购物车{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.deleteShoppingCart(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }
}
