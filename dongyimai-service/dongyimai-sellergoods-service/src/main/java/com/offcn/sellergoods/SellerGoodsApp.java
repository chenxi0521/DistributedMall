package com.offcn.sellergoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chenxi
 * @date 2021/11/5 15:58
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.sellergoods.dao")
public class SellerGoodsApp {
    public static void main(String[] args){
        SpringApplication.run(SellerGoodsApp.class, args);
    }
}
