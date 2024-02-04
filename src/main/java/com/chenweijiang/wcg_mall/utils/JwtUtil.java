package com.chenweijiang.wcg_mall.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "com.cwj.wcg.mall";  // 定义私有静态常量KEY，用于生成token的密钥

    // 接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()  // 创建JWT实例
                .withClaim("claims", claims)  // 设置payload中的claims字段
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))  // 设置token的过期时间为当前时间加1小时
                .sign(Algorithm.HMAC256(KEY));  // 使用HMAC256算法和密钥KEY对token进行签名
    }

    // 接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))  // 创建JWTVerifier实例，使用HMAC256算法和密钥KEY进行验证
                .build()  // 构建JWTVerifier实例
                .verify(token)  // 验证token的签名
                .getClaim("claims")  // 获取payload中的claims字段
                .asMap();  // 将claims字段转换为Map类型并返回
    }

}