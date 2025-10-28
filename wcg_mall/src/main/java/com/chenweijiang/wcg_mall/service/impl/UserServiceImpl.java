package com.chenweijiang.wcg_mall.service.impl;

import cn.hutool.http.HttpUtil;
import com.chenweijiang.wcg_mall.constant.JwtClaimsConstant;
import com.chenweijiang.wcg_mall.constant.RedisConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.exception.CodeException;
import com.chenweijiang.wcg_mall.mapper.KeyPairsMapper;
import com.chenweijiang.wcg_mall.mapper.UserMapper;
import com.chenweijiang.wcg_mall.pojo.KeyPairs;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.dto.UserRegisterDTO;
import com.chenweijiang.wcg_mall.properties.FrontProperties;
import com.chenweijiang.wcg_mall.properties.JuHeLoginConfigProperties;
import com.chenweijiang.wcg_mall.properties.JwtProperties;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    private final KeyPairsMapper keyPairsMapper;
    
    private final MailUtils mailUtils;
    
    private final StringRedisTemplate stringRedisTemplate;
    
    private final RedisTemplate redisTemplate;
    private final JuHeLoginConfigProperties juHeLoginConfigProperties;
    private final FrontProperties frontProperties;
    private final JwtProperties jwtProperties;
    @Override
    public JuHeLoginResponse getJuHeAuth(Integer type) {
        String userUid = new Date().getTime() + "_" + type + "_" + UUID.randomUUID();
        String apiUrl = juHeLoginConfigProperties.getLoginUrl()
                + "?id=" + juHeLoginConfigProperties.getId()
                + "&key=" + juHeLoginConfigProperties.getKey()
                + "&return=" + juHeLoginConfigProperties.getReturnUrl()+ userUid
                + "&type=" + type;
        String result = HttpUtil.get(apiUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        new JuHeLoginResponse();
        JuHeLoginResponse juHeLoginResponse;
        try {
            juHeLoginResponse = objectMapper.readValue(result, JuHeLoginResponse.class);
            redisTemplate.opsForValue().set(userUid, juHeLoginResponse.getCxid(),5, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return juHeLoginResponse;
    }

    @Override
    public void checkJuHeLogin(String userUid, HttpServletResponse httpServletResponse) throws IOException {
        if(!redisTemplate.hasKey(userUid)){
            String errorMessage = java.net.URLEncoder.encode("登录过期", "UTF-8");
            httpServletResponse.sendRedirect(frontProperties.getUrl() +"login?code=400&message=" + errorMessage);
            return;
        }
        String cxid = Objects.requireNonNull(redisTemplate.opsForValue().get(userUid)).toString();
        if(cxid == null || cxid.isEmpty()){
            String errorMessage = java.net.URLEncoder.encode("登录过期", "UTF-8");
            httpServletResponse.sendRedirect(frontProperties.getUrl() +"login?code=400&message=" + errorMessage);
            return;
        }
        String checkUrl = juHeLoginConfigProperties.getCheckLoginUrl()
                + "?id=" + juHeLoginConfigProperties.getId()
                + "&key=" + juHeLoginConfigProperties.getKey()
                + "&cxid=" + cxid;
        String result = HttpUtil.get(checkUrl);
        ObjectMapper  objectMapper = new ObjectMapper();
        JuHeCheckLoginResponse juHeCheckLoginResponse;
        try {
            juHeCheckLoginResponse = objectMapper.readValue(result, JuHeCheckLoginResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (juHeCheckLoginResponse.getCode() != 200) {
            String errorMessage = java.net.URLEncoder.encode("登录失败，"+juHeCheckLoginResponse.getMsg(), "UTF-8");
            httpServletResponse.sendRedirect(frontProperties.getUrl() +"login?code=400&message=" + errorMessage);
            return;
        }
        redisTemplate.delete(userUid);

        User user = userMapper.selectUserBySocialId(juHeCheckLoginResponse.getSocial_uid());
        if (ObjectUtils.isEmpty(user)) {
            // 保存账号信息
            user = User.builder()
                    .socialId(juHeCheckLoginResponse.getSocial_uid())
                    .type(juHeCheckLoginResponse.getType())
                    .username(juHeCheckLoginResponse.getNickname())
                    .nickName(juHeCheckLoginResponse.getNickname())
                    .password(User.DEFAULT_PASSWORD)
                    .avatar(juHeCheckLoginResponse.getFaceimg())
                    .status(User.ACTIVATED)
                    .state(User.USER)
                    .build();
            userMapper.insertUser(user);
        }
        // 登录
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        //把token放到redis中，并设置2小时有效期
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token,2, TimeUnit.HOURS);
        httpServletResponse.sendRedirect(frontProperties.getUrl() + "?token=" + token);
    }

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
                    .type("email")
                    .nickName(userRegisterDTO.getNickName())
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
            String code = String.valueOf(1000+ new Random().nextInt(9000));
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(user.getEmail(),code,5, TimeUnit.MINUTES);
            mailUtils.sendAcactivateMail(code,user.getEmail());
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
    public void activateUser(String email, String username, String phone, String avatar) {
        userMapper.updateUserByEmail(email,username,phone,avatar);
    }

    @Override
    public int activateCode(String email,String code) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String activateCode = operations.get(email);
        if(activateCode == null || !activateCode.equals(code)) {
            return 0;
        }
        return 1;
    }

    @Override
    public void getCode(String email) {
        String code = String.valueOf(1000+ new Random().nextInt(9000));
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.set(email,code,5, TimeUnit.MINUTES);
        mailUtils.sendAcactivateMail(code,email);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updatePassword(String newPassword) {
        String publicKey = keyPairsMapper.getPublicKey(BaseContext.getCurrentId());
        try{
            String RSAPassword = RSAEncryptionUtil.encryptData(newPassword, publicKey);
            userMapper.updateUserPassword(RSAPassword,BaseContext.getCurrentId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void resetPassword(Long id) {
        String publicKey = keyPairsMapper.getPublicKey(id);
        try {
            String RSAPassword = RSAEncryptionUtil.encryptData(User.DEFAULT_PASSWORD, publicKey);
            userMapper.updateUserPassword(RSAPassword, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int resetPasswordByUserEmail(String email, String code) {
        if(Objects.equals(stringRedisTemplate.opsForValue().get(RedisConstant.RESET_PWD + email), code)){
            String publicKey = keyPairsMapper.getPublicKey(userMapper.findUserByEmail(email).getId());
            try {
                String RSAPassword = RSAEncryptionUtil.encryptData(User.DEFAULT_PASSWORD, publicKey);
                userMapper.updateUserPassword(RSAPassword, userMapper.findUserByEmail(email).getId());
                log.info("删除Redis中的REST_PWD");
                stringRedisTemplate.delete(RedisConstant.RESET_PWD+email);
                return 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            return 0;
        }

    }

    @Override
    public void getCodeByResetPwd(String email) {
        String code = String.valueOf(1000+ new Random().nextInt(9000));
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.set(RedisConstant.RESET_PWD+email,code,5, TimeUnit.MINUTES);
        mailUtils.sendResetPwdMail(code,email);
    }

    @Override
    public int activateCodeByRestPwd(String email, String code) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String activateCode = operations.get(RedisConstant.RESET_PWD+email);
        if(activateCode == null || !activateCode.equals(code)) {
            return 0;
        }

        return 1;
    }
}
