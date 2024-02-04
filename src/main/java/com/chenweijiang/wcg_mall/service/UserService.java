package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.User;

public interface UserService {
    User getById(Long id);

    User findUserByEmail(String email);

    Long saveUser(String email, String password);

    public boolean checkPassword(String loginPwd,User loginUser);
}
