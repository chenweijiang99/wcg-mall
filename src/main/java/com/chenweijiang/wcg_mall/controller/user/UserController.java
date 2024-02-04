package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.pojo.User;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.UserService;
import com.chenweijiang.wcg_mall.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user/user")
@Tag(name = "用户相关接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询用户信息")
    public Result<User> getById(@PathVariable Long id){
        log.info("根据id查询用户信息，id = {}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result<String> login(String email, String password){
        log.info("用户登录，邮箱 = {}", email, password);
        User loginUser = userService.findUserByEmail(email);
        if(loginUser == null) {
            return Result.error("用户不存在");
        }

        if(userService.checkPassword(password,loginUser)){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }


    @PostMapping("/register")
    public Result<String> register(String email, String password){
        log.info("用户注册，邮箱 = {}", email, password);
        User user = userService.findUserByEmail(email);
        if( user == null){
            userService.saveUser(email, password);
            return Result.success("注册成功");
        }else {
            return Result.error("用户已存在");
        }
    }
}
