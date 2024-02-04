package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.KeyPairsMapper;
import com.chenweijiang.wcg_mall.mapper.UserMapper;
import com.chenweijiang.wcg_mall.pojo.KeyPairs;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.dto.UserRegisterDTO;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.MailUtils;
import com.chenweijiang.wcg_mall.utils.RSAEncryptionUtil;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private KeyPairsMapper keyPairsMapper;
    @Autowired
    private MailUtils mailUtils;
    @Override
    public User getById(Long id) {
         User  user =userMapper.getById(id);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User  user = userMapper.findUserByEmail(email);
        return user;
    }

    @Override
    public Long saveUser(UserRegisterDTO userRegisterDTO) {
        try {
            Map<String, String> keyPair = RSAEncryptionUtil.generateRSAKeyPair();
            String RSAPassword = RSAEncryptionUtil.encryptData(userRegisterDTO.getPassword(), keyPair.get("publicKey"));
            User user = User.builder()
                    .nickname(userRegisterDTO.getNickName())
                    .email(userRegisterDTO.getEmail())
                    .password(RSAPassword)
                    .build();
            //保存用户
            userMapper.saveUser(user);
            KeyPairs keyPairs = KeyPairs.builder()
                    .userId(user.getId())
                    .publicKey(keyPair.get("publicKey"))
                    .privateKey(keyPair.get("privateKey"))
                    .build();
            //保存密钥
            keyPairsMapper.addKeyPairs(keyPairs);
            //发送激活邮件
            mailUtils.sendAcactivateMail(user.getEmail());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean checkPassword(String loginPwd,User loginUser) {
        try {
            //获取私钥
            String privateKey = keyPairsMapper.getPrivateKey(loginUser.getId());
            //解密后判断
            if(loginPwd.equals(RSAEncryptionUtil.decryptData(loginUser.getPassword(), privateKey))){
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  false;
    }

    @Override
    public void activateUser(String email) {
        userMapper.activateUser(email);
    }
}
