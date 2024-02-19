package com.chenweijiang.wcg_mall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class WcgMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(WcgMallApplication.class, args);
		System.out.println("接口调试路径：http://localhost:8080/doc.html#/home");
	}

}
