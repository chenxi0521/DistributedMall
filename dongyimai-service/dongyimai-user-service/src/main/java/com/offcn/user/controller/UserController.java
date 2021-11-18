package com.offcn.user.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.user.pojo.User;
import com.offcn.user.service.UserService;
import com.offcn.utils.JwtUtil;
import com.offcn.utils.PhoneFormatCheckUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenxi
 * @date 2021/11/15 19:42
 * @description
 */
@RestController
@RequestMapping("/user")
@MapperScan("com.offcn.user.dao")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/sendCode/{phone}")
    public Result sendSmsCode(@PathVariable(value = "phone") String phoneNum){
        if (phoneNum == null){
            return new Result(false,StatusCode.ERROR,"手机号不能为空");
        }

        if (!PhoneFormatCheckUtils.isPhoneLegal(phoneNum)){
            return new Result(false,StatusCode.ERROR,"手机格式不正确");
        }

        try {
            userService.createSmsCode(phoneNum);
            return new Result(true, StatusCode.OK,"发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"发送失败");
        }

    }

    @RequestMapping("/add")
    public Result add(@RequestBody User user, String smscode){
        if (user != null || user.getUsername()!=null || user.getPassword()!=null ||user.getPhone()!=null){
            return new Result(false,StatusCode.ERROR,"请完成填写数据");
        }
        boolean b = userService.checkSmsCode(user.getPhone(), smscode);
        if (!b){
            return new Result(false,StatusCode.ERROR,"验证码错误");
        }

        try {
            userService.add(user);
            return new Result(true,StatusCode.OK,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"添加失败");
        }


    }

    @RequestMapping("/login")
    public Result login(String username, String password, HttpServletResponse response){
        User user = userService.findUserByUsername(username);
        //if (user == null){
        //    return new Result(false,StatusCode.LOGINERROR,"账号不存在");
        //}

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(user != null && encoder.matches(password, user.getPassword())){

            Map<String,String> map = new HashMap<>();
            map.put("role", "USER");
            map.put("success", "SUCCESS");
            map.put("username", username);

            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(map), null);
            Cookie cookie = new Cookie("Authorization", jwt);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new Result(true,StatusCode.OK,"登录成功",jwt);
        }

        return new Result(false,StatusCode.LOGINERROR,"账号或密码错误");
    }




}
