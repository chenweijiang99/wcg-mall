package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.pojo.ProductCategory;
import com.chenweijiang.wcg_mall.pojo.dto.CategoryDTO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoryController")
@Slf4j
@RequestMapping("/admin/category")
@Tag(name = "管理分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    @Operation(summary = "分类列表")
    public Result<List<ProductCategory>> list() {
        log.info("获取分类列表{}");
        List<ProductCategory> list = categoryService.list();
        if(list == null || list.size() == 0){
            return Result.error("未查询到数据");
        }
        return Result.success(list);
    }

    @PutMapping
    @Operation(summary = "修改分类")
    public Result<String> updateCategory(@RequestBody ProductCategory productCategory) {
        log.info("修改分类{}",productCategory);
        int result = categoryService.update(productCategory);
        if(result == 0){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

    @PostMapping
    @Operation(summary = "添加分类")
    public Result<String> addCategory(@RequestBody ProductCategory productCategory) {
        log.info("添加分类{}",productCategory);
        int result = categoryService.add(productCategory);
        if(result == 0){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @DeleteMapping
    @Operation(summary = "删除分类")
    public Result<String> deleteCategory(Long id) {
        log.info("删除分类{}",id);
        int result = categoryService.deleteById(id);
        if(result == 0){
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }
}
