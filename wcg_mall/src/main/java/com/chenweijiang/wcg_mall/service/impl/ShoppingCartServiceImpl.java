package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.exception.ProductNotFoundException;
import com.chenweijiang.wcg_mall.mapper.ProductMapper;
import com.chenweijiang.wcg_mall.mapper.ShoppingCartMapper;
import com.chenweijiang.wcg_mall.mapper.WishListMapper;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.ShoppingCart;
import com.chenweijiang.wcg_mall.pojo.UserWishList;
import com.chenweijiang.wcg_mall.pojo.vo.ShoppingCartVO;
import com.chenweijiang.wcg_mall.pojo.vo.WishListVO;
import com.chenweijiang.wcg_mall.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    
    private final ShoppingCartMapper shoppingCartMapper;
    
    private final ProductMapper productMapper;
    
    private final WishListMapper wishListMapper;
    @Override
    public List<ShoppingCartVO> listByUserId(Long userId) {
        return shoppingCartMapper.listByUserId(userId);
    }

    @Override
    public void addShoppingCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, productId);
        Product product = productMapper.getProductById(productId);
        if (shoppingCart == null) {
            if(product == null ){
                throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_ON_SALE);
            }
            if(product.getInventory() <= 1){
                throw new ProductNotFoundException(MessageConstant.PRODUCT_INVENTORY_NOT_ENOUGH);
            }
            ShoppingCart addShoppingCart = ShoppingCart.builder()
                    .userId(userId)
                    .productId(productId)
                    .number(1)
                    .build();
            shoppingCartMapper.addShoppingCart(addShoppingCart);

        } else {
            if(product.getInventory() <= shoppingCart.getNumber()){
                throw new ProductNotFoundException(MessageConstant.PRODUCT_INVENTORY_NOT_ENOUGH);
            }
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateShoppingCart(shoppingCart);
        }
    }

    @Override
    public void deleteShoppingCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, productId);
        if (shoppingCart != null) {
            shoppingCart.setNumber(shoppingCart.getNumber() - 1);
            if (shoppingCart.getNumber() <= 0) {
                shoppingCartMapper.deleteShoppingCart(shoppingCart.getId());
            } else {
                shoppingCartMapper.updateShoppingCart(shoppingCart);
            }
        }else {
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_IN_CART);
        }
    }

    @Override
    public void reduceProduct(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, productId);
        if (shoppingCart != null) {
            shoppingCart.setNumber(shoppingCart.getNumber() - 1);
            if (shoppingCart.getNumber() <= 0) {
                shoppingCartMapper.deleteShoppingCart(shoppingCart.getId());
            } else {
                shoppingCartMapper.updateShoppingCart(shoppingCart);
            }
        }else {
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_IN_CART);
        }
    }

    @Override
    public void deleteShoppingCartData(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, productId);
        if (shoppingCart != null) {
            shoppingCartMapper.deleteShoppingCart(shoppingCart.getId());
        }else {
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_IN_CART);
        }
    }

    @Override
    public void addProduct(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, productId);
        if (shoppingCart != null) {
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateShoppingCart(shoppingCart);
        }else {
            throw new ProductNotFoundException(MessageConstant.PRODUCT_NOT_IN_CART);
        }
    }

    @Override
    public void emptyShoppingCartData(Long userId) {
        shoppingCartMapper.deleteShoppingCartByUserId(userId);
    }

    @Override
    public void addCarProductToWishList(Long userId, Long productId) {
        List<WishListVO> listByUserId = wishListMapper.getListByUserId(userId);
        listByUserId.forEach(wishListVO -> {
            if(wishListVO.getProductId().equals(productId)){
                throw new ProductNotFoundException(MessageConstant.PRODUCT_ALREADY_IN_WISHLIST);
            }
        });
        if(wishListMapper.addToWishList(userId, productId) > 0){
            shoppingCartMapper.deleteShoppingCartByUserIDAndProductID(userId,productId);
        }
    }

    @Override
    public void deleteShoppingCartByUserIdAndProductId(Long userId, Long productId) {
        shoppingCartMapper.deleteShoppingCartByUserIDAndProductID(userId,productId);
    }
}
