package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.pojo.ProductBrand;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminBrandController")
@Slf4j
@RequestMapping("/admin/brand")
@Tag(name = "管理品牌相关接口")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    @Operation(summary = "品牌列表")
    public Result<List<ProductBrand>> list() {
        log.info("获取品牌列表");
        List<ProductBrand> brandList = brandService.list();
        if(brandList == null || brandList.size() == 0){
            return Result.error("暂无品牌");
        }
        return Result.success(brandList);
    }
    @PutMapping
    @Operation(summary = "修改品牌")
    public Result<String> updateBrand(@RequestBody ProductBrand productBrand) {
        log.info("修改品牌");
        int update = brandService.update(productBrand);
        if(update == 0){
            return Result.error("修改品牌失败");
        }
        return Result.success("修改品牌成功");
    }

    @PostMapping
    @Operation(summary = "添加品牌")
    public Result<String> addBrand(@RequestBody ProductBrand productBrand) {
        log.info("添加品牌");
        int add = brandService.add(productBrand);
        if(add == 0){
            return Result.error("添加品牌失败");
        }
        return Result.success("添加品牌成功");
    }

    @DeleteMapping
    @Operation(summary = "删除品牌")
    public Result<String> deleteBrand(Long id) {
        log.info("删除品牌");
        int delete = brandService.deleteById(id);
        if(delete == 0){
            return Result.error("删除品牌失败");
        }
        return Result.success("删除品牌成功");
    }
}
