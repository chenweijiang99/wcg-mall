package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.CategoryMapper;
import com.chenweijiang.wcg_mall.pojo.ProductCategory;
import com.chenweijiang.wcg_mall.pojo.dto.CategoryDTO;
import com.chenweijiang.wcg_mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<ProductCategory> list() {
        List<ProductCategory> productCategories = categoryMapper.list();
        return productCategories;
    }

    @Override
    public int update(ProductCategory productCategory) {
        if(categoryMapper.update(productCategory) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public int add(ProductCategory productCategory) {
        if(categoryMapper.add(productCategory) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        if(categoryMapper.deleteById(id) == 1){
            return 1;
        }
        return 0;
    }


}
