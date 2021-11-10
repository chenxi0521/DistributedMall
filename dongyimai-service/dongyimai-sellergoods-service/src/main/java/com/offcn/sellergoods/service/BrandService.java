package com.offcn.sellergoods.service;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/5 16:49
 * @description
 */
public interface BrandService {

    List<Brand> findAllBrands();

    Brand findBrandByid(Long id);

    void addBrand(Brand brand);

    void editBrand(Brand brand);

    void deleteBrandById(Long id);

    List<Brand> findList(Brand brand);

    PageResult<Brand> findPage(int page, int size);

    PageResult<Brand> findPage(Brand brand, int page, int size);

    List<Map> selectOptions();
}
