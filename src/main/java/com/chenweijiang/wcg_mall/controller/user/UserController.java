package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.JwtClaimsConstant;
import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.pojo.dto.UserRegisterDTO;
import com.chenweijiang.wcg_mall.properties.JwtProperties;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.AliOssUtil;
import com.chenweijiang.wcg_mall.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/user")
@Tag(name = "用户相关接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    JwtProperties jwtProperties;
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询用户信息")
    public Result<User> getById(@PathVariable Long id){
        log.info("根据id查询用户信息，id = {}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(String email,String password){
        log.info("用户登录{}", email, password);
        User loginUser = userService.findUserByEmail(email);
        if(loginUser == null) {
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(loginUser.getState() == 0){
            return Result.error(MessageConstant.ACCOUNT_NOT_ACTIVE);
        }
        if(userService.checkPassword(password, loginUser)){
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, loginUser.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            //把token放到redis中，并设置2小时有效期
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,2, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error(MessageConstant.PASSWORD_ERROR);
    }


    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册 {}", userRegisterDTO);
        User user = userService.findUserByEmail(userRegisterDTO.getEmail());
        if( user == null){
            userService.saveUser(userRegisterDTO);
            return Result.success(MessageConstant.REGISTER_SUCCESS_PLEASE_ACTIVATE);
        }else {
            return Result.error(MessageConstant.ALREADY_EXISTS);
        }
    }

    @PostMapping ("/activate")
    @Operation(summary = "激活用户")
    public Result<String> activateUser(String email,String username,String phone,String avatar){
        log.info("激活用户{}", email);
        userService.activateUser(email, username, phone, avatar);
        return Result.success(MessageConstant.ACCOUNT_ACTIVE_SUCCESS);
    }

    @PostMapping("/activateCode")
    @Operation(summary = "验证激活码")
    public Result<String> activateCode(String email,String code){
        log.info("验证激活码{},{}", email,code);
        if(userService.activateCode(email,code) == 1){
            return Result.success(MessageConstant.ACCOUNT_ACTIVE_SUCCESS);
        }
        return Result.error(MessageConstant.CODE_OUT_OF_TIME);
    }

    @GetMapping("/getCode")
    @Operation(summary = "获取验证码")
    public Result getCode(String email){
        log.info("获取验证码{}", email);
        userService.getCode(email);
        return Result.success();
    }
    @GetMapping("/userInfo")
    @Operation(summary = "获取用户信息")
    public Result<User> userInfo(){
        log.info("获取用户信息");
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public Result<String> logout(@RequestHeader("Authorization") String token){
        log.info("用户登出");
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success(MessageConstant.SUCCESS_LOGOUT);
    }

    // 上传文件的接口
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> upload(MultipartFile file){
        // 判断文件是否为空
        if(file.isEmpty() || file == null){
            return Result.error(MessageConstant.FILE_IS_NULL);
        }
        log.info("文件上传：{}",file);

        try {
            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 保证文件的名字是唯一的，防止覆盖
            String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
            // 存储到本地
            // file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
            // 调用AliOssUtil工具类上传文件到阿里云OSS，并获取文件的URL
            String url = aliOssUtil.upload(filename, file.getInputStream());
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }


    @PostMapping("/update")
    @Operation(summary = "更新用户信息")
    public Result<String> update(@RequestBody User user){
        log.info("更新用户信息{}", user);
        userService.updateUser(user);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    @PostMapping("/editPassword")
    @Operation(summary = "修改密码")
    public Result<String> editPassword(String oldPassword,String newPassword){
        log.info("修改密码{},{}", oldPassword,newPassword);
        User user = userService.getById(BaseContext.getCurrentId());
        if(userService.checkPassword(oldPassword, user)){
            userService.updatePassword(newPassword);
            return Result.success(MessageConstant.SUCCESS_EDIT_PASSWORD);
        }
        return Result.error(MessageConstant.PASSWORD_ERROR);
    }
}
