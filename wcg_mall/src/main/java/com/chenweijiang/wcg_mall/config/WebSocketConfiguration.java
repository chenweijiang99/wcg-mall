package com.chenweijiang.wcg_mall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类，用于注册WebSocket的Bean
 * @author 枳枳
 */
@Configuration
@Slf4j
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("正在加载WebSocket配置：{}",this);
        return new ServerEndpointExporter();
    }

}
