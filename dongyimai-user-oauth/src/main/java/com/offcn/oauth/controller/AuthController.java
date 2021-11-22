package com.offcn.oauth.controller;


import com.offcn.oauth.service.AuthService;
import com.offcn.oauth.utils.AuthToken;
import com.offcn.oauth.utils.Result;
import com.offcn.oauth.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/17 19:48
 * @description
 */
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> map, HttpServletResponse response){

        String username = map.get("username");
        String password = map.get("password");
        String clientId = map.get("clientId");
        String clientSecret = map.get("clientSecret");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return new Result(false , StatusCode.LOGINERROR,"账号和密码不能为空");
        }

        try {
            AuthToken authToken = authService.login(username, password, clientId, clientSecret);
            Cookie cookie = new Cookie("Authorization", authToken.getAccessToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(true, StatusCode.OK,"登录成功",authToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false , StatusCode.LOGINERROR,e.getMessage());
        }


    }

}
