package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductDetailDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductPageDTO;
import com.chenweijiang.wcg_mall.result.PageResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<Product> filter(ProductFilterDTO productFilterDTO);

    int addProduct(Product product);

    List<Product> list();

    int deleteProductById(Long id);

    int updateProduct(Product product);

    int stopOrStart(Long id);

    List<Product> userGetList();

    ProductDetailDTO getById(Long id);

    int addToWishList(Long userId, Long id);

    void decreaseProductInventory(Long id, Integer productNumber);

    void increaseProductInventory(Long id, Integer productNumber);

    Product getProductById(Long productId);

    List<Product> search(String searchQuery);

    PageResult<Product> userGetListPage(Integer pageNum, Integer pageSize, String searchQuery);

    PageInfo<Product> selectPage(Integer pageNum, Integer pageSize, ProductPageDTO productPageDTO);
}
