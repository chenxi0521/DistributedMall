package com.offcn.sms.config;

import com.offcn.sms.utils.SmsUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenxi
 * @date 2021/11/15 19:09
 * @description
 */
@Configuration
public class SimpleQueueConfig {

    private String simpleQueue = "dongyimai.sms.queue";

    @Bean
    public Queue getSimpleQueue(){
        return new Queue(simpleQueue);
    }



}
