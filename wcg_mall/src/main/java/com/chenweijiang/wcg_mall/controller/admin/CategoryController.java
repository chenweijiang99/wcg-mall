package com.chenweijiang.wcg_mall.controller.admin;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 管理员分类相关接口
 * @author 枳枳
 */
@RestController("adminCategoryController")
@Slf4j
@RequestMapping("/admin/category")
@Tag(name = "管理员分类相关接口")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    private final RedisTemplate redisTemplate;

    @GetMapping
    @Operation(summary = "分类列表")
    public Result<List<ProductCategory>> list() {
        log.info("获取分类列表{}");
        List<ProductCategory> list = categoryService.list();
        if(list == null || list.size() == 0){
            return Result.error(MessageConstant.CATEGORY_NOT_FOUND);
        }
        return Result.success(list);
    }

    @PutMapping
    @Operation(summary = "修改分类")
    public Result<String> updateCategory(@RequestBody ProductCategory productCategory) {
        log.info("修改分类{}",productCategory);
        int result = categoryService.update(productCategory);
        if(result == 0){
            return Result.error(MessageConstant.CATEGORY_UPDATE_FAILED);
        }
        redisTemplate.delete(RedisConstant.CATEGORY_LIST);
        return Result.success(MessageConstant.CATEGORY_UPDATE_SUCCESS);
    }

    @PostMapping
    @Operation(summary = "添加分类")
    public Result<String> addCategory(@RequestBody ProductCategory productCategory) {
        log.info("添加分类{}",productCategory);
        int result = categoryService.add(productCategory);
        if(result == 0){
            return Result.error(MessageConstant.CATEGORY_ADD_FAILED);
        }
        redisTemplate.delete(RedisConstant.CATEGORY_LIST);
        return Result.success(MessageConstant.CATEGORY_ADD_SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "删除分类")
    public Result<String> deleteCategory(Long id) {
        log.info("删除分类{}",id);
        int result = categoryService.deleteById(id);
        if(result == 0){
            return Result.error(MessageConstant.CATEGORY_DELETE_FAILED);
        }
        redisTemplate.delete(RedisConstant.CATEGORY_LIST);
        return Result.success(MessageConstant.CATEGORY_DELETE_SUCCESS);
    }
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
