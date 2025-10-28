package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.AddressMapper;
import com.chenweijiang.wcg_mall.pojo.AddressBook;
import com.chenweijiang.wcg_mall.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
   
    private final AddressMapper addressMapper;
    @Override
    public List<AddressBook> listByUserId(Long userId) {
        return addressMapper.listByUserId(userId);
    }

    @Override
    public void add(AddressBook addressBook) {
        addressMapper.add(addressBook);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressMapper.update(addressBook);
    }

    @Override
    public void deleteById(Long id) {
        addressMapper.deleteById(id);
    }
}
