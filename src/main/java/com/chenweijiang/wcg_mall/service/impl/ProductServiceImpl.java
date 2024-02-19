package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.ProductMapper;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.service.ProductService;
import com.chenweijiang.wcg_mall.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> filter(ProductFilterDTO productFilterDTO) {
        List<Product> productList = productMapper.filter(productFilterDTO);
        return productList;
    }

    @Override
    public int addProduct(Product product) {
        if(productMapper.addProduct(product) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Product> list() {
        List<Product> productList = productMapper.list();
        return productList;
    }

    @Override
    public int deleteProductById(Long id) {
        if(productMapper.deleteProductById(id) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateProduct(Product product) {
        if(productMapper.updateProduct(product) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public int stopOrStart(Long id) {
        if(productMapper.stopOrStart(id) == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Product> userGetList() {
        List<Product> productList = productMapper.userGetList();
        return productList;
    }

    @Override
    public Product getById(Long id) {
        return productMapper.getById(id);
    }

    @Override
    public int addToWishList(Long userId, Long id) {
        if(productMapper.addToWishList(userId, id) > 0){
            return 1;
        }
        return 0;
    }
}
