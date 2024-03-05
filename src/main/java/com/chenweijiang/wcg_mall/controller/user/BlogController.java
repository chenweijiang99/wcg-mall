package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user/blog")
@Slf4j
@Tag(name = "用户博客相关接口")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    @Operation(summary = "获取博客列表")
    public Result<List<Blog>> list(){
        log.info("获取文章列表");
        //TODO  redis缓存，业务代码完成后开启，测试阶段为了方便暂时关闭
//        List<Blog> list = (List<Blog>) redisTemplate.opsForValue().get(RedisConstant.BLOG_LIST);
//        if(list != null && list.size() > 0){
//            return Result.success(list);
//        }
        List<Blog> blogList = blogService.userGetList();
        if(blogList == null || blogList.size() == 0){
            return Result.error(MessageConstant.BLOG_LIST_NOT_FOUND);
        }
//        redisTemplate.opsForValue().set(RedisConstant.BLOG_LIST,blogList);
        return Result.success(blogList);
    }

    @GetMapping("/userBlog")
    @Operation(summary = "获取用户博客")
    public Result<List<Blog>> userBlog(){
        log.info("获取用户博客");
        Long userId = BaseContext.getCurrentId();
        List<Blog> blogList = blogService.userGetListByUserId(userId);
        if(blogList == null || blogList.size() == 0){
            return Result.error(MessageConstant.BLOG_LIST_NOT_FOUND);
        }
        return Result.success(blogList);
    }

    @GetMapping("/getLatestBlog")
    @Operation(summary = "获取最新博客")
    public Result<List<Blog>> getLatestBlog(){
        log.info("获取最新博客");
        List<Blog> blogList = blogService.getLatestBlog();
        if(blogList == null || blogList.size() == 0){
            return Result.error(MessageConstant.BLOG_LIST_NOT_FOUND);
        }
        return Result.success(blogList);
    }

    @GetMapping("/getRelatedBlog")
    @Operation(summary = "获取相关博客")
    public Result<List<Blog>> getRelatedBlog(){
        log.info("获取相关博客");
        Long userId = BaseContext.getCurrentId();
        List<Blog> blogList = blogService.getRelatedBlog(userId);
        if(blogList == null || blogList.size() == 0){
            return Result.error(MessageConstant.BLOG_LIST_NOT_FOUND);
        }
        return Result.success(blogList);
    }
    @GetMapping("/getBlogDetail")
    @Operation(summary = "获取博客详情")
    public Result<BlogDetailVO> getBlogDetail(Long id){
        log.info("获取博客详情{}",id);
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(id);
        if(blogDetailVO == null){
            return Result.error(MessageConstant.BLOG_NOT_FOUND);
        }
        return Result.success(blogDetailVO);
    }
    @PostMapping
    @Operation(summary = "发表博客")
    public Result<String> addBlog(String title,String content,String image){
        log.info("发表博客{}",title,content,image);
        Blog blog = Blog.builder()
                .title(title)
                .content(content)
                .image(image)
                .build();
        blogService.addBlog(blog);
//        cleanCache(RedisConstant.BLOG_LIST);
        return Result.success();
    }
    @DeleteMapping
    @Operation(summary = "删除博客")
    public Result<String> deleteBlog(Long id){
        log.info("删除博客{}",id);
        Blog blog = blogService.findById(id);
        if(blog == null){
            return Result.error(MessageConstant.BLOG_NOT_FOUND);
        }
        if(blog.getCreateUser() != BaseContext.getCurrentId()){
            return Result.error(MessageConstant.BLOG_DELETE_FAILED);
        }
        blogService.deleteBlog(id);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改博客")
    public Result<String> updateBlog(@RequestBody Blog blog){
        log.info("修改博客{}",blog);
        blogService.updateBlog(blog);
        return Result.success();
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
