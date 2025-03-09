package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.BlogService;
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
 * 管理员博客相关接口
 * @author 枳枳
 */

@RestController("adminBlogController")
@RequestMapping("/admin/blog")
@Tag(name = "管理员博客相关接口")
@Slf4j
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    private final RedisTemplate redisTemplate;


    @GetMapping
    @Operation(summary = "获取博客列表")
    public Result<List<BlogDetailVO>> getBlogList() {
        log.info("获取博客列表");
        List<BlogDetailVO> blogList = blogService.getBlogList();
        return Result.success(blogList);
    }

    @DeleteMapping
    @Operation(summary = "删除博客")
    public Result<String> deleteBlog(Long id) {
        log.info("删除博客{}",id);
        blogService.deleteBlog(id);
        cleanCache(RedisConstant.BLOG_LIST);
        return Result.success();
    }
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}


