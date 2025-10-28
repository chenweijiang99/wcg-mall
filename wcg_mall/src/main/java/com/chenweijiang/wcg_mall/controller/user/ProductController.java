package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.IndexSlider;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.ShopSlider;
import com.chenweijiang.wcg_mall.pojo.dto.ProductDetailDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductPageDTO;
import com.chenweijiang.wcg_mall.result.PageResult;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.IndexService;
import com.chenweijiang.wcg_mall.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户商品接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/product")
@Slf4j
@Tag(name = "商品相关接口")
@RequiredArgsConstructor
public class ProductController {
    
    private  final  ProductService productService;
    
    private final RedisTemplate redisTemplate;
    
    private final IndexService indexService;

    @GetMapping("/getShopSlider")
    @Operation(summary = "获取轮播图数据")
    public Result<List<ShopSlider>> getShopSlider(){
        log.info("获取轮播图数据");
        List<ShopSlider> list = (List<ShopSlider>) redisTemplate.opsForValue().get(RedisConstant.SHOP_SLIDER);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        List<ShopSlider> shopSliderList = indexService.getShopSlider();
        redisTemplate.opsForValue().set(RedisConstant.SHOP_SLIDER,shopSliderList);
        return Result.success(shopSliderList);
    }
    @PostMapping("/filter")
    @Operation(summary = "商品筛选")
    public Result<List<Product>> filter(@RequestBody ProductFilterDTO productFilterDTO){
       log.info("商品筛选:{}", productFilterDTO);
        List<Product> productList = productService.filter(productFilterDTO);
        return Result.success(productList);
    }

    @GetMapping
    @Operation(summary = "商品列表")
    public Result<List<Product>> userGetList(){
        log.info("获取商品列表");
        //从redis中获取商品列表
        List<Product> list = (List<Product>) redisTemplate.opsForValue().get(RedisConstant.PRODUCT_LIST);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }
        //从数据库中获取商品列表
        List<Product> productList = productService.userGetList();
        if(productList == null || productList.size() == 0){
            return Result.error(MessageConstant.PRODUCT_LIST_NOT_FOUND);
        }
        redisTemplate.opsForValue().set(RedisConstant.PRODUCT_LIST,productList);
        return Result.success(productList);
    }

//    @GetMapping("/page")
//    @Operation(summary = "商品列表")
//    public Result<PageInfo<Product>> userGetListPage(
//                        String name,
//                        Integer[] category,
//                        Integer minPrice,
//                        Integer maxPrice,
//                        Integer[] brand,
//                        @RequestParam(defaultValue = "1") Integer pageNum,
//                        @RequestParam(defaultValue = "8") Integer pageSize) {
//        log.info("获取商品列表分页");
//        Product product = Product.builder()
//                .name(name)
//                .category(category)
//                .minPrice(minPrice)
//                .maxPrice(maxPrice)
//                .brand(brand)
//                .status(1)
//                .build();
//        PageInfo<Product> pageResult = productService.selectPage(pageNum, pageSize,product);
//        return Result.success(pageResult);
//    }

    @PostMapping("/page")
    @Operation(summary = "分页获取商品列表")
    public Result<PageInfo<Product>> userGetListPage(
            @RequestBody ProductPageDTO productPageDTO,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        log.info("获取商品列表分页");
        PageInfo<Product> pageResult = productService.selectPage(pageNum, pageSize,productPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/getProductDetail")
    @Operation(summary = "商品详情")
    public Result<ProductDetailDTO> userGetProduct(Long id){
        log.info("获取商品详情:{}", id);
        ProductDetailDTO product = productService.getById(id);
        if(product == null){
            return Result.error(MessageConstant.PRODUCT_NOT_FOUND);
        }
        return Result.success(product);
    }

    @PutMapping("/addToWishList/{id}")
    @Operation(summary = "添加商品到心愿单")
    public Result<String> addToWishList(@PathVariable Long id){
        log.info("添加商品到心愿单:{}", id);

        Long userId = BaseContext.getCurrentId();
        int result = productService.addToWishList(userId,id);
        if (result == 0){
            return Result.error(MessageConstant.ADD_TO_WISH_LIST_FAILED);
        }else {
            return Result.success(MessageConstant.ADD_TO_WISH_LIST_SUCCESS);
        }
    }

    //搜索
    @GetMapping("/search")
    @Operation(summary = "搜索商品")
    public Result<List<Product>> search(String searchQuery){
        log.info("搜索商品:{}", searchQuery);
        List<Product> productList = productService.search(searchQuery);
        return Result.success(productList);
    }
}
