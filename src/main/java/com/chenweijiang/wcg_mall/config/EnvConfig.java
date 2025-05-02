package com.chenweijiang.wcg_mall.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:.env") // 加载 .env 文件
public class EnvConfig {


}