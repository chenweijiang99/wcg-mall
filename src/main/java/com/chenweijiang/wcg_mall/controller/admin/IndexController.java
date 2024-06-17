package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.IndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("adminIndexController")
@Tag(name = "首页接口")
@RequestMapping("/admin/index")
@Slf4j
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private RedisTemplate redisTemplate;
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
