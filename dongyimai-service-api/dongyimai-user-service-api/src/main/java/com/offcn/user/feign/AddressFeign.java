package com.offcn.user.feign;

import com.offcn.entity.Result;
import com.offcn.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/19 21:03
 * @description
 */
@FeignClient("dym-user")
@RequestMapping("/address")
public interface AddressFeign {

    @GetMapping("/findListByUserId")
    Result<List<Address>> findListByUserId();

}
