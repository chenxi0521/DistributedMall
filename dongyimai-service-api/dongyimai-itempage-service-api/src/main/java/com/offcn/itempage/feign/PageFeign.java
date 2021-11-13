package com.offcn.itempage.feign;

import com.offcn.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenxi
 * @date 2021/11/12 19:28
 * @description
 */
@FeignClient("dym-itempage")
@RequestMapping("/page")
public interface PageFeign {
    @RequestMapping("/createPageHtml/{id}")
    Result createPageHtml(@PathVariable(value = "id") Long goodsId);
}
