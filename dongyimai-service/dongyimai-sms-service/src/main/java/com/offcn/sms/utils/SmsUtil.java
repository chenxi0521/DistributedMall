package com.offcn.sms.utils;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/15 19:12
 * @description
 */
@Component
public class SmsUtil {

    @Value("${sms.appcode}")
    private String appcode;

    @Value("${sms.tpl_id}")
    private String tpl_id;

    private String host = "http://dingxin.market.alicloudapi.com";
    private String path = "/dx/sendSms";
    private String method = "POST";


    public HttpResponse sendSms(String mobile,String code){
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("param", "code:"+code);
        querys.put("tpl_id", tpl_id);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
           return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
