package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.exception.ProductNotFoundException;
import com.chenweijiang.wcg_mall.exception.WishListAlreadyExistsException;
import com.chenweijiang.wcg_mall.mapper.ProductMapper;
import com.chenweijiang.wcg_mall.mapper.WishListMapper;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.UserWishList;
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
    @Autowired
    private WishListMapper wishListMapper;
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
         Product product = productMapper.getById(id);
         if(product == null){
             throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_FOUND);
         }
         if(product.getStatus() == 0){
             throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_ON_SALE);
         }
        List<UserWishList> userWishLists = wishListMapper.getListByUserId(userId);
        userWishLists.forEach(userWishList -> {
            if(userWishList.getProductId().equals(id)){
                throw new WishListAlreadyExistsException(MessageConstant.WISH_LIST_IS_ALREADY_EXISTS);
            }
        });
        if(productMapper.addToWishList(userId, id) > 0){
            return 1;
        }
        return 0;
    }
}
