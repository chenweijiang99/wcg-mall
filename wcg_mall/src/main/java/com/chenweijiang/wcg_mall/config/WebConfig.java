package com.chenweijiang.wcg_mall.config;

import com.chenweijiang.wcg_mall.interceptor.AdminJwtTokenInterceptor;
import com.chenweijiang.wcg_mall.interceptor.UserJwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置类
 * @author 枳枳
 */

@Configuration  // 声明该类是一个配置类
@Slf4j
@RequiredArgsConstructor
//EnableWebMvc
public class  WebConfig implements WebMvcConfigurer {  // 定义WebConfig类并实现WebMvcConfigurer接口

    private final UserJwtTokenInterceptor jwtTokenInterceptor;

    private final AdminJwtTokenInterceptor jwtAdminTokenInterceptor;

    String [] excludePaths = new String[]{
            "/user/alipay/**",
            "/user/user/login",
            "/user/user/juhe/**",
            "/user/user/register",
            "/user/user/activate",
            "/user/user/activateCode",
            "/user/user/getCode",
            "/user/user/resetPassword",
            "/user/user/getCodeByResetPwd",
            "/user/user/activateCodeByRestPwd",
            "/user/user/upload",
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
        log.info("正在加载拦截器:{}",this);
        // 登录接口和注册接口不拦截
        resolvers.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns(excludePaths);

        resolvers.addInterceptor(jwtAdminTokenInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user/login");
    }
}
