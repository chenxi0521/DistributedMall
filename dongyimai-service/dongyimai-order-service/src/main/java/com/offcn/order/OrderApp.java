package com.offcn.order;

import com.offcn.utils.IdWorker;
import com.offcn.utils.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author chenxi
 * @date 2021/11/18 21:31
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.offcn.order.dao")
@EnableFeignClients(basePackages = {"com.offcn.sellergoods.feign","com.offcn.user.feign"})
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }


    @Bean
    TokenDecode tokenDecode(){
        return new TokenDecode();
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
