package com.chenweijiang.wcg_mall.config;


import com.chenweijiang.wcg_mall.interceptor.JwtAdminTokenInterceptor;
import com.chenweijiang.wcg_mall.interceptor.JwtTokenInterceptor;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Autowired  // 自动装配LoginInterceptor实例
    private JwtTokenInterceptor jwtTokenInterceptor;  // 声明一个名为loginInterceptor的LoginInterceptor类型的私有变量
    @Autowired
    private JwtAdminTokenInterceptor jwtAdminTokenInterceptor;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("文创购物商城API接口文档")
                        // 接口文档简介
                        .description("这是基于Knife4j OpenApi3的接口文档")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("陈韦江").email("1774532899@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot基础框架")
                        .url("http://127.0.0.1:8080"));
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    String [] excludePaths = new String[]{
            "/user/user/login",
            "/user/user/register",
            "/user/user/activate",
            "/user/user/activateCode",
            "/user/user/getCode",
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
        // 登录接口和注册接口不拦截
        resolvers.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns(excludePaths);

        resolvers.addInterceptor(jwtAdminTokenInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user/login");
    }

}