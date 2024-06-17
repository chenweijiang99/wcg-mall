package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.vo.OfficialCollectionVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.BlogService;
import com.chenweijiang.wcg_mall.service.IndexService;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "首页接口")
@RequestMapping("/user/index")
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/getOL")
    @Operation(summary = "获取首页官方收藏数据")
    public Result<List<OfficialCollectionVO>> index() {
        log.info("获取首页官方收藏数据");
        List<OfficialCollectionVO> list = (List<OfficialCollectionVO>) redisTemplate.opsForValue().get(RedisConstant.OL_LIST);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        List<OfficialCollectionVO> officialCollectionVOS = indexService.userGetOL();
        redisTemplate.opsForValue().set(RedisConstant.OL_LIST,officialCollectionVOS);
        return Result.success(officialCollectionVOS);
    }
    @GetMapping("/getNewProduct")
    @Operation(summary = "获取首页最新商品数据")
    public Result<List<Product>> getNewProduct() {
        log.info("获取首页最新商品数据");
        List<Product> products = (List<Product>) redisTemplate.opsForValue().get(RedisConstant.PRODUCT_LIST);
        if(products == null ){
           products = productService.userGetList();
        }
        List<Product> newProducts = products.stream()
                .sorted(Comparator.comparing(Product::getCreateTime).reversed())
                .limit(6)
                .collect(Collectors.toList());
        return Result.success(newProducts);
    }

    @GetMapping("/getNewBlog")
    @Operation(summary = "获取首页最新博客数据")
    public Result<List<Blog>> getNewBlog() {
        log.info("获取首页最新博客数据");
        List<Blog> blogs = (List<Blog>) redisTemplate.opsForValue().get(RedisConstant.BLOG_LIST);
        if(blogs == null ){
            blogs = blogService.userGetList();
        }
        List<Blog> newBlogs = blogs.stream()
                .sorted(Comparator.comparing(Blog::getCreateTime).reversed())
                .limit(3)
                .collect(Collectors.toList());
        return Result.success(newBlogs);
    }

}
