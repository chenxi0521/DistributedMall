package com.offcn.oauth.utils;

import java.io.Serializable;

/**
 * @author chenxi
 * @date 2021/11/17 17:12
 * @description
 */
public class AuthToken implements Serializable {


    //令牌信息（jwt）
    private String accessToken;

    //刷新token(refresh_token)
    private String refreshToken;

    //jti: jwt的唯一身份标识
    private String jti;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
