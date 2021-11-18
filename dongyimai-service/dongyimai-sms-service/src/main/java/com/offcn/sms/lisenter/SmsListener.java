package com.offcn.sms.lisenter;

import com.offcn.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/15 19:18
 * @description
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;


    @RabbitListener(queues = "dongyimai.sms.queue")
    public void getMessage(Map<String,String> massage){

        if (massage == null){
            return;
        }
        String mobile = massage.get("mobile");
        String code = massage.get("code");
        smsUtil.sendSms(mobile, code);
    }
}
