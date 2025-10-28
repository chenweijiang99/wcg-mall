package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.ProductBrand;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.BrandService;
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
 * 管理员品牌相关接口
 * @author 枳枳
 */
@RestController("adminBrandController")
@Slf4j
@RequestMapping("/admin/brand")
@Tag(name = "管理品牌相关接口")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    private final RedisTemplate redisTemplate;
    @GetMapping
    @Operation(summary = "品牌列表")
    public Result<List<ProductBrand>> list() {
        log.info("获取品牌列表");
        List<ProductBrand> brandList = brandService.list();
        if(brandList == null || brandList.size() == 0){
            return Result.error(MessageConstant.BRAND_LIST_IS_NULL);
        }
        return Result.success(brandList);
    }
    @PutMapping
    @Operation(summary = "修改品牌")
    public Result<String> updateBrand(@RequestBody ProductBrand productBrand) {
        log.info("修改品牌");
        int update = brandService.update(productBrand);
        if(update == 0){
            return Result.error(MessageConstant.BRAND_UPDATE_FAILED);
        }
        cleanCache(RedisConstant.BRAND_LIST);
        return Result.success(MessageConstant.BRAND_UPDATE_SUCCESS);
    }

    @PostMapping
    @Operation(summary = "添加品牌")
    public Result<String> addBrand(@RequestBody ProductBrand productBrand) {
        log.info("添加品牌");
        int add = brandService.add(productBrand);
        if(add == 0){
            return Result.error(MessageConstant.BRAND_ADD_FAILED);
        }
        cleanCache(RedisConstant.BRAND_LIST);
        return Result.success(MessageConstant.BRAND_ADD_SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "删除品牌")
    public Result<String> deleteBrand(Long id) {
        log.info("删除品牌");
        int delete = brandService.deleteById(id);
        if(delete == 0){
            return Result.error(MessageConstant.BRAND_DELETE_FAILED);
        }
        cleanCache(RedisConstant.BRAND_LIST);
        return Result.success(MessageConstant.BRAND_DELETE_SUCCESS);
    }

    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
