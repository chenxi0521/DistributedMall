package com.offcn.user.feign;

import com.offcn.entity.Result;
import com.offcn.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenxi
 * @date 2021/11/18 19:39
 * @description
 */
@FeignClient("dym-user")
@RequestMapping("/user")
public interface UserFeign {


    @GetMapping("/findUserByUserName/{username}")
    public Result<User> findUserByUserName(@PathVariable("username") String username);

    @PutMapping("/addPoints")
    public Result addPoints(@RequestParam("points")Integer points);

}
