package com.chenweijiang.wcg_mall.controller.user;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户品牌相关接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/brand")
@Slf4j
@Tag(name = "用户品牌相关接口")
@RequiredArgsConstructor // Lombok注解，生成构造函数
public class BrandController {
    private final BrandService brandService;
    private final RedisTemplate redisTemplate;
    @GetMapping
    @Operation(summary = "品牌列表")
    public Result<List<ProductBrand>> list() {
        log.info("获取品牌列表");
        //从redis中获取品牌列表
        List<ProductBrand> list = (List<ProductBrand>) redisTemplate.opsForValue().get(RedisConstant.BRAND_LIST);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        //从数据库中获取品牌列表
        List<ProductBrand> brandList = brandService.list();
        if(brandList == null || brandList.size() == 0){
            return Result.error(MessageConstant.BRAND_LIST_IS_NULL);
        }
        redisTemplate.opsForValue().set(RedisConstant.BRAND_LIST, brandList);
        return Result.success(brandList);
    }
}
