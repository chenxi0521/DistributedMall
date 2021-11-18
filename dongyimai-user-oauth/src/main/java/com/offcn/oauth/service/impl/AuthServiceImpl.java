package com.offcn.oauth.service.impl;

import com.offcn.oauth.service.AuthService;
import com.offcn.oauth.utils.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/17 17:23
 * @description
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {

        ServiceInstance instance = loadBalancerClient.choose("user-auth");
        if (instance == null){
            throw new RuntimeException("找不到对应服务实例");
        }

        String url = instance.getUri().toString() + "/oauth/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", httpbasic(clientId, clientSecret));

        //如果想捕捉服务本身抛出的异常信息，需要通过自行实现RestTemplate的ErrorHandler。
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        Map map = null;
        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, header), Map.class);
            map = responseEntity.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }

        if (map !=null && !StringUtils.isEmpty(map.get("access_token"))){

            String accessToken = map.get("access_token")+"";
            String refreshToken = map.get("refresh_token")+"";
            String jti = map.get("jti")+"";

            AuthToken authToken = new AuthToken();
            authToken.setAccessToken(accessToken);
            authToken.setRefreshToken(refreshToken);
            authToken.setJti(jti);

            return authToken;
        }

        throw new RuntimeException("令牌创建失败");
    }


    private String httpbasic(String clientId, String clientSecret){
        String s = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(s.getBytes());
        return "Basic "+ new String(encode);

    }

}
