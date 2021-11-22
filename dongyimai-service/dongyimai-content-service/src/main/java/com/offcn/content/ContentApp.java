package com.offcn.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenxi
 * @date 2021/11/10 19:58
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.offcn.content.com.offcn.order.dao")
public class ContentApp {
    public static void main(String[] args) {
        SpringApplication.run(ContentApp.class, args);
    }
}
