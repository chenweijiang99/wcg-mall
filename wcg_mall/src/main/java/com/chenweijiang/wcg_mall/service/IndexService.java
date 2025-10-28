package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.IndexSlider;
import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.ShopSlider;
import com.chenweijiang.wcg_mall.pojo.vo.OfficialCollectionVO;

import java.util.List;

public interface IndexService {
    void setProductAsOfficialCollection(Long id);

    void unSetProductAsOfficialCollection(Long id);

    List<OfficialCollection> getOL();

    List<OfficialCollectionVO> userGetOL();

    List<Product> userGetNewProduct();

    List<IndexSlider> getIndexSlider();

    int deleteIndexSlider(Long id);

    int addIndexSlider(String url);

    List<ShopSlider> getShopSlider();

    int deleteShopSlider(Long id);

    int addShopSlider(String url);
}
