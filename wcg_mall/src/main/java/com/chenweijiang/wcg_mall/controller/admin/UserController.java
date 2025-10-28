package com.chenweijiang.wcg_mall.controller.admin;

import com.chenweijiang.wcg_mall.constant.JwtClaimsConstant;
import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.properties.JwtProperties;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.AliOssUtil;
import com.chenweijiang.wcg_mall.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 管理员接口
 * @author 枳枳
 */
@RestController("adminUserController")
@Slf4j
@RequestMapping("/admin/user")
@Tag(name = "管理员相关接口")
@RequiredArgsConstructor
public class UserController {
   
    private final UserService userService;
   
    private final StringRedisTemplate stringRedisTemplate;
   
    private final JwtProperties jwtProperties;
   
    private final AliOssUtil aliOssUtil;


    @GetMapping
    @Operation(summary = "获取用户信息")
    public Result<User> getUserInfo(){
        log.info("获取用户信息");
        User user = userService.getById(BaseContext.getCurrentId());
        return Result.success(user);
    }
    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<String> login(String email,String password){
        log.info("管理员登录{}",email);
        User user = userService.findUserByEmail(email);
        if(user == null){
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(user.getStatus() != User.ADMIN){
            return Result.error(MessageConstant.ACCOUNT_NOT_ADMIN);
        }
        if (user.getState() == 0){
            return Result.error(MessageConstant.ACCOUNT_NOT_ACTIVE);
        }
        if(userService.checkPassword(password,user)){
            //生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.ADMIN_ID, user.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);
            //把token放到redis中，并设置2小时有效期
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,2, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error(MessageConstant.PASSWORD_ERROR);
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

    @GetMapping("/getUserList")
    @Operation(summary = "获取用户列表")
    public Result<List<User>> getUserList(){
        log.info("获取用户列表");
        List<User> userList = userService.getUserList();
        return Result.success(userList);
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public Result<String> resetPassword(Long id){
        log.info("重置密码{}",id);
        userService.resetPassword(id);
        return Result.success();
    }
    @DeleteMapping("")
    @Operation(summary = "删除用户")
    public Result<String> deleteUser(Long id){
        log.info("删除用户{}",id);
        userService.deleteById(id);
        return Result.success();
    }
}
