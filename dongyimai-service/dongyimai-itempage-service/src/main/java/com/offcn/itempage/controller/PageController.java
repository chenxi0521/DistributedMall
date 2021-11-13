package com.offcn.itempage.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.itempage.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxi
 * @date 2021/11/12 16:29
 * @description
 */
@RestController
@RequestMapping("page")
public class PageController {

    @Autowired
    private PageService pageService;


    @RequestMapping("/createPageHtml/{id}")
    public Result createPageHtml(@PathVariable(value = "id") Long goodsId){
        try {
            pageService.createPageHtml(goodsId);
            return new Result(true, StatusCode.OK,"创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"创建失败");
        }
    }


}
