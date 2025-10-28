package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.ProductCategory;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户分类相关接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/category")
@Tag(name = "用户分类相关接口")
@Slf4j
@RequiredArgsConstructor // Lombok注解，生成构造函数
public class CategoryController {

    private final CategoryService categoryService;
    private final RedisTemplate redisTemplate;
    @GetMapping
    @Operation(summary = "获取分类列表")
    public Result<List<ProductCategory>> list(){
        log.info("获取分类列表");
        //从redis中获取
        List<ProductCategory> list = (List<ProductCategory>) redisTemplate.opsForValue().get(RedisConstant.CATEGORY_LIST);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        //从数据库中获取
        List<ProductCategory> productCategories = categoryService.list();
        if(productCategories == null || productCategories.size() == 0){
            return Result.error(MessageConstant.CATEGORY_UPDATE_FAILED);
        }
        redisTemplate.opsForValue().set(RedisConstant.CATEGORY_LIST, productCategories);
        return Result.success(productCategories);
    }

}
