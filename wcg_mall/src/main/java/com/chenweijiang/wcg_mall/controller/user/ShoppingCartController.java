package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户购物车接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "购物车接口")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "获取购物车数据")
    public Result<List<ShoppingCartVO>> listByUserId(){
        log.info("获取购物车数据");
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCartVO> shoppingCartList = shoppingCartService.listByUserId(userId);
        return Result.success(shoppingCartList);

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
        log.info("删除购物车商品{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.deleteShoppingCart(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "删除数据")
    public Result<String> deleteShoppingCartData(Long productId){
        //log.info("删除购物车{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.deleteShoppingCartData(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }


    @PostMapping("/reduceProduct")
    @Operation(summary = "减少购物车数据")
    public Result<String> reduceProduct(Long productId){
        log.info("减少购物车数据{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.reduceProduct(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }

    @PostMapping("/addProduct")
    @Operation(summary = "增加购物车数据")
    public Result<String> addProduct(Long productId){
        log.info("增加购物车数据{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.addProduct(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }


    @PostMapping("/addCarProductToWishList")
    @Operation(summary = "购物车商品添加到心愿单")
    public Result<String> addCarProductToWishList(Long productId){
        log.info("购物车商品添加到心愿单{}",productId);
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.addCarProductToWishList(userId,productId);
        return Result.success(MessageConstant.SUCCESS);
    }
}
