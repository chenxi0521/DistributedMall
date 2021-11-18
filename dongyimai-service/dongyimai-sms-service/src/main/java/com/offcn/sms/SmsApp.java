package com.offcn.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author chenxi
 * @date 2021/11/15 20:24
 * @description
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SmsApp {
    public static void main(String[] args){
        SpringApplication.run(SmsApp.class, args);
    }
}
