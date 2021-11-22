package com.offcn.sellergoods.feign;

import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/10 22:30
 * @description
 */
@FeignClient("dym-sellergoods")
@RequestMapping("/item")
public interface ItemFeign {

    @GetMapping("/status/{status}")
    Result<List<Item>> findByStatus(@PathVariable(name = "status")String status);

    @GetMapping("/{id}")
     Result<Item> findById(@PathVariable("id") Long id);

    @PutMapping("/decrCount/{userId}")
    Result decrCount(@PathVariable("userId") String userId);

}
