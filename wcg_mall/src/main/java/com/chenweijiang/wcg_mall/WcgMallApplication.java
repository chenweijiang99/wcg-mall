package com.chenweijiang.wcg_mall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableCaching //开启缓存
@EnableScheduling //开启任务调度
@EnableTransactionManagement //开启注解方式的事务管理
public class WcgMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(WcgMallApplication.class, args);
		System.out.println(" __      __                  _ _                                               __      _ _      \n" +
				" \\ \\    / /_ __ _ _ __  __ _| | |  _ _ _  _ _ _  ___  ____  _ __ __ ___ ______/ _|_  _| | |_  _ \n" +
				"  \\ \\/\\/ / _/ _` | '  \\/ _` | | | | '_| || | ' \\(_-< (_-< || / _/ _/ -_|_-<_-<  _| || | | | || |\n" +
				"   \\_/\\_/\\__\\__, |_|_|_\\__,_|_|_| |_|  \\_,_|_||_/__/ /__/\\_,_\\__\\__\\___/__/__/_|  \\_,_|_|_|\\_, |\n" +
				"            |___/                                                                          |__/ ");
		System.out.println("接口调试路径：http://localhost:1203/doc.html#/home");
	}

}
