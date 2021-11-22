package com.offcn.user;

import com.offcn.utils.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author chenxi
 * @date 2021/11/15 20:22
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.offcn.user.dao")
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }


    @Bean
    TokenDecode tokenDecode(){
        return new TokenDecode();
    }

}
