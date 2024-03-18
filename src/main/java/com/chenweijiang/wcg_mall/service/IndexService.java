package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.vo.OfficialCollectionVO;

import java.util.List;

public interface IndexService {
    void setProductAsOfficialCollection(Long id);

    void unSetProductAsOfficialCollection(Long id);

    List<OfficialCollection> getOL();

    List<OfficialCollectionVO> userGetOL();

    List<Product> userGetNewProduct();
}
