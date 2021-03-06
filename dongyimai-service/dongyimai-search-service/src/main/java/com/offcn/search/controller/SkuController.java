package com.offcn.search.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/10 22:44
 * @description
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("/import")
    public Result importSku(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"ES导入数据成功");
    }

    @RequestMapping("/clear")
    public Result clearAll(){
        skuService.clearAll();
        return new Result(true, StatusCode.OK,"清除数据成功");
    }

    @PostMapping
    public Map search(@RequestBody(required = false) Map searchMap){
        Map result = skuService.search(searchMap);
        return result;
    }

}
