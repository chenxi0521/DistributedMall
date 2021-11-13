package com.offcn.sellergoods.feign;

import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.ItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenxi
 * @date 2021/11/12 15:50
 * @description
 */
@FeignClient(value = "dym-sellergoods")
@RequestMapping("/itemCat")
public interface ItemCatFeign {

    @GetMapping("/{id}")
    public Result<ItemCat> findById(@PathVariable(value = "id") Long id);
}
