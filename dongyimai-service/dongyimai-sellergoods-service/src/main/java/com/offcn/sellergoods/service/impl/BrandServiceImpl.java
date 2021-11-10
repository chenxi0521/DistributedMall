package com.offcn.sellergoods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.entity.PageResult;
import com.offcn.sellergoods.dao.BrandMapper;
import com.offcn.sellergoods.pojo.Brand;
import com.offcn.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/5 16:49
 * @description
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService  {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有Brand数据
     * @return 返回list<Brand>数据
     */
    @Override
    public List<Brand> findAllBrands() {
        return this.list();
    }

    /**
     * 根据id查询Brand数据
     * @param id 查询条件
     * @return
     */
    @Override
    public Brand findBrandByid(Long id) {
        return this.getById(id);
    }

    /**
     * 添加品牌数据
     * @param brand 新增数据
     */
    @Override
    public void addBrand(Brand brand) {
        this .checkBrand(brand);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", brand.getName());
        List<Brand> brands = this.list(queryWrapper);
        if (brands != null && brands.size()>0){
            throw new RuntimeException("提交品牌名称已存在,请检查数据是否有误!");
        }
        this.save(brand);
    }

    /**
     * 修改品牌数据
     * @param brand 修改数据
     */
    @Override
    public void editBrand(Brand brand) {
        this.checkBrand(brand);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", brand.getName()).notIn("id", brand.getId());
        List<Brand> brands = this.list(queryWrapper);
        if (brands != null && brands.size()>0){
            throw new RuntimeException("提交品牌名称已存在,请检查数据是否有误!");
        }
        this.updateById(brand);
    }

    /**
     * 根据id删除品牌数据
     * @param id 删除条件
     * @return
     */
    @Override
    public void deleteBrandById(Long id) {
        this.removeById(id);
    }

    /**
     * 条件查询品牌数据
     * @param brand 查询条件
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        QueryWrapper<Brand> queryWrapper = this.createQueryWrapper(brand);
        List<Brand> brands = list(queryWrapper);
        return brands;
    }

    /**
     * 分页查询品牌数据
     * @param page 页码
     * @param size 每页条数
     * @return
     */
    @Override
    public PageResult<Brand> findPage(int page, int size) {
        Page pageclazz = new Page(page, size);
        Page mypage = this.page(pageclazz);

        PageResult<Brand> result = new PageResult<>();
        result.setTotal(mypage.getTotal());
        result.setRows(mypage.getRecords());

        return result;
    }

    /**
     * 分页条件查询品牌数据
     * @param brand 查询条件
     * @param page 页码
     * @param size 每页条数
     * @return
     */
    @Override
    public PageResult<Brand> findPage(Brand brand, int page, int size) {
        QueryWrapper<Brand> queryWrapper = this.createQueryWrapper(brand);
        Page pageclazz = new Page(page, size);
        Page mypage = this.page(pageclazz,queryWrapper);

        PageResult<Brand> result = new PageResult<>();
        result.setTotal(mypage.getTotal());
        result.setRows(mypage.getRecords());
        return result;
    }

    @Override
    public List<Map> selectOptions() {
        return brandMapper.selectOptions();
    }


    /**
     * 检查上传brand数据是否符合要求
     * @param brand
     * @return
     */
    public boolean checkBrand(Brand brand){
        if (brand == null){
            throw new RuntimeException("提交数据为空,请填写数据后再提交!");
        }

        if (brand.getName() == null || StringUtils.isEmpty(brand.getName())){
            throw new RuntimeException("品牌名称不能为空或者空字符串!");
        }

        if (StringUtils.isEmpty(brand.getFirstChar())){
            throw new RuntimeException("品牌首字母不能为空");
        }else if (brand.getFirstChar().length()!= 1){
            throw new RuntimeException("品牌首字母长度不能大于1");
        }
        return true;
    }

    /**
     * 根据上传数据构建QueryWrapper对象
     * @param brand
     * @return
     */
    public QueryWrapper<Brand> createQueryWrapper(Brand brand){
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if (brand.getId() != null){
            queryWrapper.eq("id", brand.getId());
        }

        if (!StringUtils.isEmpty(brand.getName())){
            queryWrapper.like("name", brand.getName());
        }

        if (!StringUtils.isEmpty(brand.getFirstChar())){
            queryWrapper.eq("first_char", brand.getFirstChar());
        }
        return queryWrapper;
    }
}
