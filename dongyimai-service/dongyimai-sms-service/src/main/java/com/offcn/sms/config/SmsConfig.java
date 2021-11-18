package com.offcn.sms.config;

import com.offcn.sms.utils.SmsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxi
 * @date 2021/11/15 20:56
 * @description
 */
@Configuration
public class SmsConfig {

    @Bean
    public SmsUtil smsUtil(){
        return new SmsUtil();
    }
}
