package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;

import java.util.List;

public interface ProductService {
    List<Product> filter(ProductFilterDTO productFilterDTO);

    int addProduct(Product product);

    List<Product> list();

    int deleteProductById(Long id);

    int updateProduct(Product product);

    int stopOrStart(Long id);

    List<Product> userGetList();

    Product getById(Long id);

    int addToWishList(Long userId, Long id);
}
