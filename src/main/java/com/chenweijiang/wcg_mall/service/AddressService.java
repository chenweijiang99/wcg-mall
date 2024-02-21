package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.AddressBook;

import java.util.List;

public interface AddressService {
    List<AddressBook> listByUserId(Long userId);

    void add(AddressBook addressBook);

    void update(AddressBook addressBook);

    void deleteById(Long id);
}
