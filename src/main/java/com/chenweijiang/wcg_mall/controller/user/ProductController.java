package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.exception.AddToWishListException;
import com.chenweijiang.wcg_mall.exception.ProductNotFoundException;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/product")
@Slf4j
@Tag(name = "商品相关接口")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/filter")
    @Operation(summary = "商品筛选")
    public Result<List<Product>> filter(@RequestBody ProductFilterDTO productFilterDTO){
       log.info("商品筛选:{}", productFilterDTO);
        List<Product> productList = productService.filter(productFilterDTO);
        return Result.success(productList);
    }

    @GetMapping
    @Operation(summary = "商品列表")
    public Result<List<Product>> userGetList(){
        log.info("获取商品列表");
        //从redis中获取商品列表
        String key = "product_list";
        List<Product> list = (List<Product>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        //从数据库中获取商品列表
        List<Product> productList = productService.userGetList();
        if(productList == null || productList.size() == 0){
            return Result.error(MessageConstant.PRODUCT_LIST_NOT_FOUND);
        }
        redisTemplate.opsForValue().set(key,productList);
        return Result.success(productList);
    }

    @GetMapping("{id}")
    @Operation(summary = "商品详情")
    public Result<Product> userGetProduct(@PathVariable Long id){
        log.info("获取商品详情:{}", id);
        Product product = productService.getById(id);
        if(product == null){
            return Result.error(MessageConstant.PRODUCT_NOT_FOUND);
        }
        return Result.success(product);
    }

    @PutMapping("/addToWishList/{id}")
    @Operation(summary = "添加商品到心愿单")
    public Result<String> addToWishList(@PathVariable Long id){
        log.info("添加商品到心愿单:{}", id);

        Long userId = BaseContext.getCurrentId();
        int result = productService.addToWishList(userId,id);
        if (result == 0){
            return Result.error(MessageConstant.ADD_TO_WISH_LIST_FAILED);
        }else {
            return Result.success(MessageConstant.ADD_TO_WISH_LIST_SUCCESS);
        }
    }
}
