package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> list();


    int update(ProductCategory productCategory);

    int add(ProductCategory productCategory);

    int deleteById(Long id);
}
