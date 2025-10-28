package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 管理员商品相关接口
 * @author 枳枳
 */
@RestController("adminProductController")
@Slf4j
@RequestMapping("/admin/product")
@Tag(name = "管理商品相关接口")
@RequiredArgsConstructor
public class ProductController {

    
    private  final ProductService productService;
    
    private final RedisTemplate redisTemplate;

    @PostMapping("/addProduct")
    @Operation(summary = "添加商品")
    public Result<String> addProduct(@RequestBody Product product) {
        log.info("添加商品{}",product);
        product.setDescription(product.getDescription().replace("<p>", "").replace("</p>", ""));
        if(productService.addProduct(product) ==1){
            cleanCache(RedisConstant.PRODUCT_LIST);
            List<Product> productList = productService.userGetList();
            redisTemplate.opsForValue().set(RedisConstant.PRODUCT_LIST,productList);
            return Result.success(MessageConstant.PRODUCT_ADD_SUCCESS);
        }
        return Result.error(MessageConstant.PRODUCT_ADD_FAILED);
    }

    @GetMapping
    @Operation(summary = "商品列表")
    public Result<List<Product>> list() {
        log.info("商品列表");
        List<Product> products = productService.list();
        if(products == null || products.size() == 0){
            return Result.error(MessageConstant.PRODUCT_LIST_NOT_FOUND);
        }
        return Result.success(products);
    }

    @DeleteMapping("/delete{id}")
    @Operation(summary = "删除商品")
    public Result<String> deleteProduct(@PathVariable Long id) {
        log.info("删除商品{}",id);
        if(productService.deleteProductById(id) == 1){
            cleanCache(RedisConstant.PRODUCT_LIST);
            List<Product> productList = productService.userGetList();
            redisTemplate.opsForValue().set(RedisConstant.PRODUCT_LIST,productList);
            return Result.success(MessageConstant.PRODUCT_DELETE_FAILED);
        }
        return Result.error(MessageConstant.PRODUCT_DELETE_FAILED);
    }

    @PutMapping
    @Operation(summary = "修改商品")
    public Result<String> updateProduct(@RequestBody Product product) {
        log.info("修改商品{}", product);
        product.setDescription(product.getDescription().replace("<p>", "").replace("</p>", ""));
        if(productService.updateProduct(product) == 1){
            cleanCache(RedisConstant.PRODUCT_LIST);
            List<Product> productList = productService.userGetList();
            redisTemplate.opsForValue().set(RedisConstant.PRODUCT_LIST,productList);
            return Result.success(MessageConstant.PRODUCT_UPDATE_FAILED);
        }
        return Result.error(MessageConstant.PRODUCT_UPDATE_FAILED);
    }

    @PutMapping("/stopOrStart/{id}")
    @Operation(summary = "停用或启用商品")
    public Result<String> stopOrStartProduct(@PathVariable Long id) {
        log.info("停用或启用商品{}",id);
        if(productService.stopOrStart(id) == 1){
            cleanCache(RedisConstant.PRODUCT_LIST);
            return Result.success(MessageConstant.SUCCESS);
        }
        return Result.error(MessageConstant.FAIL);
    }

    /**
     * 删除缓存
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
