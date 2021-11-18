package com.offcn.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author chenxi
 * @date 2021/11/15 20:22
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
