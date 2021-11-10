package com.offcn.sellergoods.controller;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.group.GoodsEntity;
import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.service.GoodsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@Api(tags = "GoodsController")
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /***
     * Goods分页条件搜索实现
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Goods条件分页查询", notes = "分页条件查询Goods方法详情", tags = {"GoodsController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageResult<Goods>> findPage(@RequestBody(required = false) @ApiParam(name = "Goods对象", value = "传入JSON数据", required = false) Goods goods, @PathVariable int page, @PathVariable int size) {
        //调用GoodsService实现分页条件查询Goods
        PageResult<Goods> pageResult = goodsService.findPage(goods, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /***
     * Goods分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Goods分页查询", notes = "分页查询Goods方法详情", tags = {"GoodsController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageResult<Goods>> findPage(@PathVariable int page, @PathVariable int size) {
        //调用GoodsService实现分页查询Goods
        PageResult<Goods> pageResult = goodsService.findPage(page, size);
        return new Result<PageResult<Goods>>(true, StatusCode.OK, "查询成功", pageResult);
    }

    /***
     * 多条件搜索品牌数据
     * @param goods
     * @return
     */
    @ApiOperation(value = "Goods条件查询", notes = "条件查询Goods方法详情", tags = {"GoodsController"})
    @PostMapping(value = "/search")
    public Result<List<Goods>> findList(@RequestBody(required = false) @ApiParam(name = "Goods对象", value = "传入JSON数据", required = false) Goods goods) {
        //调用GoodsService实现条件查询Goods
        List<Goods> list = goodsService.findList(goods);
        return new Result<List<Goods>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 修改Goods数据
     * @param goods
     * @param id
     * @return
     */
    @ApiOperation(value = "Goods根据ID修改", notes = "根据ID修改Goods方法详情", tags = {"GoodsController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody @ApiParam(name = "Goods对象", value = "传入JSON数据", required = false) Goods goods, @PathVariable Long id) {
        //设置主键值
        goods.setId(id);
        //调用GoodsService实现修改Goods
        goodsService.update(goods);
        return new Result(true, StatusCode.OK, "修改成功");
    }






    /***
     * 新增Goods数据
     * @param goodsEntity
     * @return
     */
    @ApiOperation(value = "Goods添加", notes = "添加Goods方法详情", tags = {"GoodsController"})
    @PostMapping
    public Result add(@RequestBody @ApiParam(name = "Goods复合对象", value = "传入JSON数据", required = true) GoodsEntity goodsEntity) {
        //调用GoodsService实现添加Goods
        goodsService.add(goodsEntity);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询Goods数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Goods根据ID查询", notes = "根据ID查询Goods方法详情", tags = {"GoodsController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public Result<Goods> findById(@PathVariable Long id) {
        //调用GoodsService实现根据主键查询Goods
        Goods goods = goodsService.findById(id);
        return new Result<Goods>(true, StatusCode.OK, "查询成功", goods);
    }

    /**
     * 商品审核
     * @param goodsId
     * @return
     */
    @PutMapping("/audit/{goodsId}")
    public Result audit(@PathVariable("goodsId")Long goodsId) {
        try {
            goodsService.audit(goodsId);
            return new Result(true,StatusCode.OK,"审核通过");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }

    /**
     * 商品下架
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "商品下架",notes = "商品下架",tags = {"GoodsController"})
    @ApiImplicitParam(paramType = "path",name="goodsId",value = "主键id",required = true,dataType = "Long")
    @PutMapping("/pull/{goodsId}")
    public Result pull(@PathVariable("goodsId") Long goodsId){
        try {
            goodsService.pull(goodsId);
            return new Result(true,StatusCode.OK,"下架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }

    /**
     * 商品上架
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "商品上架",notes = "商品上架",tags = {"GoodsController"})
    @ApiImplicitParam(paramType = "path",name="goodsId",value = "主键id",required = true,dataType = "Long")
    @PutMapping("/put/{goodsId}")
    public Result put(@PathVariable("goodsId") Long goodsId){
        try {
            goodsService.put(goodsId);
            return new Result(true,StatusCode.OK,"上架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }

    /**
     * 商品下架
     * @param goodsIds
     * @return
     */
    @ApiOperation(value = "批量商品下架",notes = "批量商品下架",tags = {"GoodsController"})
    @PutMapping("/pullMany")
    public Result pullMany(@RequestBody Long[] goodsIds){
        try {
            int i = goodsService.pullMany(goodsIds);
            return new Result(true,StatusCode.OK,"成功下架"+i+"个商品");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }



    /**
     * 商品上架
     * @param goodsIds
     * @return
     */
    @ApiOperation(value = "批量商品上架",notes = "批量商品上架",tags = {"GoodsController"})
    @PutMapping("/putMany")
    public Result putMany(@RequestBody Long[] goodsIds){
        try {
            int i = goodsService.putMany(goodsIds);
            return new Result(true,StatusCode.OK,"成功上架"+i+"个商品");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }

    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "删除商品",notes = "删除商品",tags = {"GoodsController"})
    @ApiImplicitParam(paramType = "path",name="goodsId",value = "主键id",required = true,dataType = "Long")
    @DeleteMapping("/delete/{goodsId}")
    public Result delete(@PathVariable("goodsId") Long goodsId){
        try {
            goodsService.delete(goodsId);
            return new Result(true,StatusCode.OK,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage());
        }
    }



}
