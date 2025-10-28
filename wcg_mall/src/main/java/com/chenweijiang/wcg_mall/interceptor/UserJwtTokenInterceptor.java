package com.chenweijiang.wcg_mall.interceptor;

import com.chenweijiang.wcg_mall.constant.JwtClaimsConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.properties.JwtProperties;
import com.chenweijiang.wcg_mall.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器（用户端）
 * @author 枳枳
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserJwtTokenInterceptor implements HandlerInterceptor {


    private final StringRedisTemplate stringRedisTemplate;

    private final JwtProperties jwtProperties;
    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //令牌验证
        String token = request.getHeader(jwtProperties.getUserTokenName());

        try{
            //从redis中获取token
            log.info("jwt校验:{}", token);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null){
                throw new RuntimeException();
            }
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //把业务数据存储到ThreadLocal中
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id:{}", userId);
            BaseContext.setCurrentId(userId);
//            ThreadLocalUtil.set(claims);
            //放行
            return true;
        }catch (Exception e){
            //http响应状态码401
            response.setStatus(401);
            //不放行
            return false;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //执行完后，清空ThreadLocal中的数据
        BaseContext.removeCurrentId();
    }
}
