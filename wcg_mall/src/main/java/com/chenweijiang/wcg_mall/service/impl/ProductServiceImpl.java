package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.constant.StatusConstant;
import com.chenweijiang.wcg_mall.exception.ProductNotFoundException;
import com.chenweijiang.wcg_mall.exception.WishListAlreadyExistsException;
import com.chenweijiang.wcg_mall.mapper.ProductMapper;
import com.chenweijiang.wcg_mall.mapper.WishListMapper;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.ProductDetailDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductFilterDTO;
import com.chenweijiang.wcg_mall.pojo.dto.ProductPageDTO;
import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;
import com.chenweijiang.wcg_mall.result.PageResult;
import com.chenweijiang.wcg_mall.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final WishListMapper wishListMapper;
    @Override
    public List<Product> filter(ProductFilterDTO productFilterDTO) {
        List<Product> productList = productMapper.filter(productFilterDTO);
        return productList;
    }

    @Override
    public int addProduct(Product product) {
        product.setStatus(StatusConstant.ENABLE);
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
        Product product = productMapper.getByIdNotStatus(id);
        if(product == null){
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_FOUND);
        }
        if(product.getStatus() == 1){
            product.setStatus(StatusConstant.DISABLE);
        }else{
            product.setStatus(StatusConstant.ENABLE);
        }
        if(productMapper.updateProduct(product) > 0){
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
    public ProductDetailDTO getById(Long id) {
        return productMapper.getById(id);
    }

    @Override
    public int addToWishList(Long userId, Long id) {
         Product product = productMapper.getProductById(id);
         if(product == null){
             throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_FOUND);
         }
         if(product.getStatus() == 0){
             throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_ON_SALE);
         }
        List<WishListVO> userWishLists = wishListMapper.getListByUserId(userId);
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

    @Override
    public void decreaseProductInventory(Long id, Integer productNumber) {
        Product product = productMapper.getProductById(id);
        if(product == null){
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_FOUND);
        }
        if(product.getInventory() < productNumber || product.getInventory() <= 0 )  {
            throw new ProductNotFoundException(MessageConstant.PRODUCT_INVENTORY_NOT_ENOUGH);
        }
        product.setInventory(product.getInventory() - productNumber);
        productMapper.updateProduct(product);
    }

    @Override
    public void increaseProductInventory(Long id, Integer productNumber) {
        Product product = productMapper.getProductById(id);
        if(product == null){
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_FOUND);
        }
        product.setInventory(product.getInventory() + productNumber);
        productMapper.updateProduct(product);
    }

    @Override
    public Product getProductById(Long productId) {
       return  productMapper.getProductById(productId);
    }

    @Override
    public List<Product> search(String searchQuery) {
        searchQuery = "%" + searchQuery.replace("\"", "") + "%";
        return productMapper.search(searchQuery);
    }

    @Override
    public PageResult<Product> userGetListPage(Integer pageNum, Integer pageSize, String searchQuery) {
        PageResult<Product> pageResult = new PageResult<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.usergetListPage(searchQuery);
        Page<Product> page = (Page<Product>) productList;
        pageResult.setRecords(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public PageInfo<Product> selectPage(Integer pageNum, Integer pageSize, ProductPageDTO productPageDTO) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectAll(productPageDTO);
        return PageInfo.of(productList);
    }


}
