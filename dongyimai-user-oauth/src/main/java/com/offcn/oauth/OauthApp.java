package com.offcn.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenxi
 * @date 2021/11/17 10:37
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.offcn.user.feign")
public class OauthApp {
    public static void main(String[] args) {
        SpringApplication.run(OauthApp.class, args);
    }


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
