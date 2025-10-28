package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.IndexSlider;
import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.pojo.ShopSlider;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.IndexService;
import com.chenweijiang.wcg_mall.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 管理员首页相关接口
 * @author 枳枳
 */
@RestController("adminIndexController")
@Tag(name = "管理员首页接口")
@RequestMapping("/admin/index")
@Slf4j
@RequiredArgsConstructor
public class IndexController {
    
    private final IndexService indexService;
    
    private final RedisTemplate redisTemplate;
    
    private final AliOssUtil aliOssUtil;

    @GetMapping("/getShopSlider")
    @Operation(summary = "获取商品页轮播图数据")
    public Result<List<ShopSlider>> getShopSlider(){
        log.info("获取商品页轮播图数据");
        return Result.success(indexService.getShopSlider());
    }

    @DeleteMapping ("/deleteShopSlider")
    @Operation(summary = "删除商品页轮播图")
    public Result deleteShopSlider(Long id){
        log.info("删除商品页轮播图{}",id);
        indexService.deleteShopSlider(id);
        cleanCache(RedisConstant.SHOP_SLIDER);
        return Result.success();
    }

    @PostMapping("/addShopSlider")
    @Operation(summary = "添加商品页轮播图")
    public Result addShopSlider(String url){
        log.info("添加商品页轮播图{}",url);
        indexService.addShopSlider(url);
        cleanCache(RedisConstant.SHOP_SLIDER);
        return Result.success();
    }

    @GetMapping("/getIndexSlider")
    @Operation(summary = "获取轮播图数据")
    public Result<List<IndexSlider>> getIndexSlider(){
        log.info("获取轮播图数据");
        return Result.success(indexService.getIndexSlider());
    }

    @DeleteMapping ("/deleteIndexSlider")
    @Operation(summary = "删除轮播图")
    public Result deleteIndexSlider(Long id){
        log.info("删除轮播图{}",id);
        indexService.deleteIndexSlider(id);
        cleanCache(RedisConstant.INDEX_SLIDER);
        return Result.success();
    }

    @PostMapping("/addIndexSlider")
    @Operation(summary = "添加轮播图")
    public Result addIndexSlider(String url){
        log.info("添加轮播图{}",url);
        indexService.addIndexSlider(url);
        cleanCache(RedisConstant.INDEX_SLIDER);
        return Result.success();
    }
    @GetMapping("/getOL")
    @Operation(summary = "获取官方收藏")
    public Result<List<OfficialCollection>> getOL(){
        log.info("获取官方收藏");
        List<OfficialCollection> officialCollectionList = indexService.getOL();
        return Result.success(officialCollectionList);
    }



    @PostMapping("/setAsOL")
    @Operation(summary = "设置为官方收藏")
    public Result setProductAsOfficialCollection(Long id){
        log.info("设置为官方收藏{}", id);
        indexService.setProductAsOfficialCollection(id);
        cleanCache(RedisConstant.OL_LIST);
        return Result.success();
    }
    @PostMapping("/unSetAsOL")
    @Operation(summary = "取消为官方收藏")
    public Result unSetProductAsOfficialCollection(Long id){
        log.info("取消为官方收藏{}", id);
        indexService.unSetProductAsOfficialCollection(id);
        cleanCache(RedisConstant.OL_LIST);
        return Result.success();
    }
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
