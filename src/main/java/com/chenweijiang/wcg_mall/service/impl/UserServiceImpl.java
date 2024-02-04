package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.KeyPairsMapper;
import com.chenweijiang.wcg_mall.mapper.UserMapper;
import com.chenweijiang.wcg_mall.pojo.KeyPairs;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.RSAEncryptionUtil;
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
    public Long saveUser(String email, String password) {
        try {
            Map<String, String> keyPair = RSAEncryptionUtil.generateRSAKeyPair();
            String RSAPassword = RSAEncryptionUtil.encryptData(password, keyPair.get("publicKey"));
            User user = User.builder()
                    .email(email)
                    .password(RSAPassword)
                    .build();
            userMapper.saveUser(user);
            KeyPairs keyPairs = KeyPairs.builder()
                    .userId(user.getId())
                    .publicKey(keyPair.get("publicKey"))
                    .privateKey(keyPair.get("privateKey"))
                    .build();
            keyPairsMapper.addKeyPairs(keyPairs);
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
            String privateKey = keyPairsMapper.getPrivateKey(loginUser.getId());
            if(loginPwd.equals(RSAEncryptionUtil.decryptData(loginUser.getPassword(), privateKey))){
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  false;
    }
}
