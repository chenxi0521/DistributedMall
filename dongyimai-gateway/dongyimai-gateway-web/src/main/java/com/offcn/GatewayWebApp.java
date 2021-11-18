package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenxi
 * @date 2021/11/16 9:45
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayWebApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApp.class, args);
    }
}
