package com.chenweijiang.wcg_mall.controller.admin;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController("adminUserController")
@Slf4j
@RequestMapping("/admin/user")
@Tag(name = "管理员相关接口")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<String> login(String email,String password){
        log.info("管理员登录{}",email);
        User user = userService.findUserByEmail(email);
        if(user == null){
            return Result.error("用户不存在");
        }
        if(user.getStatus() != User.ADMIN){
            return Result.error("用户不是管理员");
        }
        if (user.getState() == 0){
            return Result.error("用户未激活");
        }
        if(userService.checkPassword(password,user)){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);
            //把token放到redis中，并设置2小时有效期
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,2, TimeUnit.HOURS);
//            BaseContext.setCurrentId(user.getId());
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    // 上传文件的接口
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> upload(MultipartFile file){
        // 判断文件是否为空
        if(file.isEmpty() || file == null){
            System.out.println("文件为空");
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
}
