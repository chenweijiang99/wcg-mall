package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.ProductBrand;

import java.util.List;

public interface BrandService {
    List<ProductBrand> list();

    int update(ProductBrand productBrand);

    int add(ProductBrand productBrand);

    int deleteById(Long id);
}
