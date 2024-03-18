package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.exception.OfficialCollectionException;
import com.chenweijiang.wcg_mall.mapper.IndexMapper;
import com.chenweijiang.wcg_mall.pojo.OfficialCollection;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.vo.OfficialCollectionVO;
import com.chenweijiang.wcg_mall.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;

    @Override
    public void setProductAsOfficialCollection(Long id) {
        OfficialCollection officialCollection = indexMapper.getOLById(id);
        if (officialCollection == null) {
            indexMapper.setProductAsOfficialCollection(id);
        }else {
            throw new OfficialCollectionException(MessageConstant.OFFICIAL_COLLECTION_ALREADY_EXISTS);
        }
    }


    @Override
    public void unSetProductAsOfficialCollection(Long id) {
        OfficialCollection officialCollection = indexMapper.getOLById(id);
        if (officialCollection != null) {
            indexMapper.unSetProductAsOfficialCollection(id);
        }else {
            throw new OfficialCollectionException(MessageConstant.OFFICIAL_COLLECTION_NOT_EXISTS);
        }

    }

    @Override
    public List<OfficialCollection> getOL() {
        return indexMapper.getOL();
    }

    @Override
    public List<OfficialCollectionVO> userGetOL() {
        return indexMapper.userGetOL();
    }

    @Override
    public List<Product> userGetNewProduct() {
        return indexMapper.getNewProduct();
    }
}
