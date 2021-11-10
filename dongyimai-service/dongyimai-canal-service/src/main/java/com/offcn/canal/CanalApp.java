package com.offcn.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenxi
 * @date 2021/11/10 19:11
 * @description
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableCanalClient
@EnableFeignClients(basePackages = {"com.offcn.content.feign"})
public class CanalApp {
    public static void main(String[] args) {
        SpringApplication.run(CanalApp.class, args);
    }
}
