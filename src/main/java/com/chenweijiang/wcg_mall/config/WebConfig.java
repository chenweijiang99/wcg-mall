package com.chenweijiang.wcg_mall.config;

import com.chenweijiang.wcg_mall.interceptor.JwtTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 声明该类是一个配置类
@Slf4j
public class WebConfig implements WebMvcConfigurer {  // 定义WebConfig类并实现WebMvcConfigurer接口
    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;


    String [] excludePaths = new String[]{
            "/user/user/login",
            "/user/user/register",
            "/user/user/activate/**",
            "/doc.html/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/api/**",
            "/v3/**",
            "/webjars/**",
            "/swagger-ui.html/**"
    };
    @Override  // 重写父类方法
    public void addInterceptors(InterceptorRegistry resolvers) {
        // 登录接口和注册接口不拦截
        resolvers.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns(excludePaths);
    }
}
