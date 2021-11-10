package com.offcn.sellergoods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.offcn.sellergoods.pojo.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/5 16:47
 * @description
 */
public interface BrandMapper extends BaseMapper<Brand> {

    @Select("select id ,name as test from tb_brand")
    public List<Map> selectOptions();
}
