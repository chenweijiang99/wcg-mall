package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/product")
@Slf4j
@Tag(name = "商品相关接口")
public class ProductController {
    @Autowired
    private ProductService productService;

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
        List<Product> productList = productService.userGetList();
        if(productList == null || productList.size() == 0){
            return Result.error("暂无商品");
        }
        return Result.success(productList);
    }

    @GetMapping("{id}")
    @Operation(summary = "商品详情")
    public Result<Product> userGetProduct(@PathVariable Long id){
        log.info("获取商品详情:{}", id);
        Product product = productService.getById(id);
        if(product == null){
            return Result.error("商品不存在");
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
            return Result.error("添加失败");
        }else {
            return Result.success("添加成功");
        }
    }
}
