package com.offcn.oauth.service;

import com.offcn.oauth.utils.AuthToken;

/**
 * @author chenxi
 * @date 2021/11/17 17:23
 * @description
 */
public interface AuthService {

    /***
     * 授权认证方法
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);

}
