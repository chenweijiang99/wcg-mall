package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminProductController")
@Slf4j
@RequestMapping("/admin/product")
@Tag(name = "管理商品相关接口")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/addProduct")
    @Operation(summary = "添加商品")
    public Result<String> addProduct(@RequestBody Product product) {
        log.info("添加商品");
        if(productService.addProduct(product) ==1){
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @GetMapping
    @Operation(summary = "商品列表")
    public Result<List<Product>> list() {
        log.info("商品列表");
        List<Product> products = productService.list();
        return Result.success(products);
    }

    @DeleteMapping("/delete{id}")
    @Operation(summary = "删除商品")
    public Result<String> deleteProduct(@PathVariable Long id) {
        log.info("删除商品");
        if(productService.deleteProductById(id) == 1){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @PutMapping
    @Operation(summary = "修改商品")
    public Result<String> updateProduct(@RequestBody Product product) {
        log.info("修改商品");
        if(productService.updateProduct(product) == 1){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    @PutMapping("/stopOrStart/{id}")
    @Operation(summary = "停用或启用商品")
    public Result<String> stopOrStartProduct(@PathVariable Long id) {
        log.info("停用或启用商品");
        if(productService.stopOrStart(id) == 1){
            return Result.success("操作成功");
        }
        return Result.error("操作失败");
    }
}
