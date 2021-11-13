package com.offcn.itempage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenxi
 * @date 2021/11/12 15:46
 * @description
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(value = "com.offcn.sellergoods.feign")
public class ItemPageApp {
    public static void main(String[] args) {
        SpringApplication.run(ItemPageApp.class, args);
    }
}
