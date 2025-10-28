package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.dto.UserRegisterDTO;
import com.chenweijiang.wcg_mall.utils.JuHeLoginResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User getById(Long id);

    User findUserByEmail(String email);

    Long saveUser(UserRegisterDTO userRegisterDTO);

    boolean checkPassword(String loginPwd, User loginUser);

    void activateUser(String email, String username, String phone, String avatar);

    int activateCode(String email, String code);

    void getCode(String email);

    void updateUser(User user);

    void updatePassword(String newPassword);

    List<User> getUserList();

    void deleteById(Long id);

    void resetPassword(Long id);

    int resetPasswordByUserEmail(String email, String code);

    void getCodeByResetPwd(String email);

    int activateCodeByRestPwd(String email, String code);

    JuHeLoginResponse getJuHeAuth(Integer type);

    void checkJuHeLogin(String cxid, HttpServletResponse httpServletResponse) throws IOException;
}
