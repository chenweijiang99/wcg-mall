package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.BrandMapper;
import com.chenweijiang.wcg_mall.pojo.ProductBrand;
import com.chenweijiang.wcg_mall.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;

    @Override
    public List<ProductBrand> list() {
        return brandMapper.list();
    }

    @Override
    public int update(ProductBrand productBrand) {
        if (brandMapper.update(productBrand) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int add(ProductBrand productBrand) {
        if (brandMapper.add(productBrand) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        if (brandMapper.deleteById(id) > 0) {
            return 1;
        }
        return 0;
    }
}
