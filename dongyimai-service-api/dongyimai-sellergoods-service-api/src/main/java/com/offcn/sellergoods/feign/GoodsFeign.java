package com.offcn.sellergoods.feign;

import com.offcn.entity.Result;
import com.offcn.sellergoods.group.GoodsEntity;
import com.offcn.sellergoods.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenxi
 * @date 2021/11/12 15:54
 * @description
 */
@FeignClient(value = "dym-sellergoods")
@RequestMapping("/goods")
public interface GoodsFeign {

    @GetMapping("/{id}")
    Result<GoodsEntity> findById(@PathVariable(value = "id") Long id);
}
