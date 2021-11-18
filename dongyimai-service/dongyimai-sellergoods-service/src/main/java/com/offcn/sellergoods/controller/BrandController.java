package com.offcn.sellergoods.controller;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.pojo.Brand;
import com.offcn.sellergoods.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/5 16:55
 * @description
 */
@RestController
@RequestMapping("/brand")
@Api(tags = {"BrandController"})
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List> findAllBrands(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);

        try {
            List<Brand> allBrands = brandService.findAllBrands();
            return new Result<List>(true, StatusCode.OK,"查询成功",allBrands);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"查询失败");
        }

    }

    @GetMapping("/{id}")
    public Result<Brand> findBrandById(@PathVariable("id") Long id){

        try {
            Brand brand = brandService.findBrandByid(id);
            return new Result<Brand>(true, StatusCode.OK,"查询成功",brand);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"查询失败");
        }

    }


    @PostMapping
    public Result addBrand(@RequestBody Brand brand){

        try {
            brandService.addBrand(brand);
            return new Result(true, StatusCode.OK,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,e.getMessage());
        }

    }

    @PutMapping
    public Result editBrand(@RequestBody Brand brand){

        try {
            brandService.editBrand(brand);
            return new Result(true, StatusCode.OK,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public Result deleteBrandById(@PathVariable("id") Long id){

        try {
            brandService.deleteBrandById(id);
            return new Result(true, StatusCode.OK,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"删除失败");
        }

    }

    @PostMapping("/search")
    public Result<List> findList(@RequestBody(required = false) Brand brand){

        try {
            List<Brand> brands = brandService.findList(brand);
            return new Result<List>(true, StatusCode.OK,"查询成功",brands);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"查询失败");
        }

    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageResult> findPage(@PathVariable("page") int page,@PathVariable("size") int size){

        try {
            PageResult<Brand> brands = brandService.findPage(page,size);
            return new Result<PageResult>(true, StatusCode.OK,"查询成功",brands);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"查询失败");
        }

    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageResult> findPage(@RequestBody(required = false) Brand brand,@PathVariable("page") int page,@PathVariable("size") int size){

        try {
            PageResult<Brand> brands = brandService.findPage(brand,page,size);
            return new Result<PageResult>(true, StatusCode.OK,"查询成功",brands);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"查询失败");
        }

    }


    @ApiOperation(value = "查询品牌下拉列表",notes = "查询品牌下拉列表",tags = {"BrandController"})
    @GetMapping("/selectOptions")
    public List<Map> selectOptions(){
        return brandService.selectOptions();
    }



}
