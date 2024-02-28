package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.dto.UserRegisterDTO;

public interface UserService {
    User getById(Long id);

    User findUserByEmail(String email);

    Long saveUser(UserRegisterDTO userRegisterDTO);

     boolean checkPassword(String loginPwd,User loginUser);

    void activateUser(String email, String username, String phone, String avatar);

    int activateCode(String email,String code);

    void getCode(String email);

    void updateUser(User user);

    void updatePassword(String newPassword);
}
