package com.offcn.sellergoods.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.entity.PageResult;
import com.offcn.sellergoods.dao.*;
import com.offcn.sellergoods.group.GoodsEntity;
import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.pojo.GoodsDesc;
import com.offcn.sellergoods.pojo.Item;
import com.offcn.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/****
 * @Author:ujiuye
 * @Description:Goods业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SellerMapper sellerMapper;

    /**
     * Goods条件+分页查询
     *
     * @param goods 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Goods> findPage(Goods goods, int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        IPage<Goods> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        IPage<Goods> iPage = this.page(mypage, new QueryWrapper<Goods>());

        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods条件查询
     *
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods) {
        //构建查询条件
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Goods构建查询对象
     *
     * @param goods
     * @return
     */
    public QueryWrapper<Goods> createQueryWrapper(Goods goods) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goods != null) {
            // 主键
            if (!StringUtils.isEmpty(goods.getId())) {
                queryWrapper.eq("id", goods.getId());
            }
            // 商家ID
            if (!StringUtils.isEmpty(goods.getSellerId())) {
                queryWrapper.eq("seller_id", goods.getSellerId());
            }
            // SPU名
            if (!StringUtils.isEmpty(goods.getGoodsName())) {
                queryWrapper.eq("goods_name", goods.getGoodsName());
            }
            // 默认SKU
            if (!StringUtils.isEmpty(goods.getDefaultItemId())) {
                queryWrapper.eq("default_item_id", goods.getDefaultItemId());
            }
            // 状态
            if (!StringUtils.isEmpty(goods.getAuditStatus())) {
                queryWrapper.eq("audit_status", goods.getAuditStatus());
            }
            // 是否上架
            if (!StringUtils.isEmpty(goods.getIsMarketable())) {
                queryWrapper.eq("is_marketable", goods.getIsMarketable());
            }
            // 品牌
            if (!StringUtils.isEmpty(goods.getBrandId())) {
                queryWrapper.eq("brand_id", goods.getBrandId());
            }
            // 副标题
            if (!StringUtils.isEmpty(goods.getCaption())) {
                queryWrapper.eq("caption", goods.getCaption());
            }
            // 一级类目
            if (!StringUtils.isEmpty(goods.getCategory1Id())) {
                queryWrapper.eq("category1_id", goods.getCategory1Id());
            }
            // 二级类目
            if (!StringUtils.isEmpty(goods.getCategory2Id())) {
                queryWrapper.eq("category2_id", goods.getCategory2Id());
            }
            // 三级类目
            if (!StringUtils.isEmpty(goods.getCategory3Id())) {
                queryWrapper.eq("category3_id", goods.getCategory3Id());
            }
            // 小图
            if (!StringUtils.isEmpty(goods.getSmallPic())) {
                queryWrapper.eq("small_pic", goods.getSmallPic());
            }
            // 商城价
            if (!StringUtils.isEmpty(goods.getPrice())) {
                queryWrapper.eq("price", goods.getPrice());
            }
            // 分类模板ID
            if (!StringUtils.isEmpty(goods.getTypeTemplateId())) {
                queryWrapper.eq("type_template_id", goods.getTypeTemplateId());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(goods.getIsEnableSpec())) {
                queryWrapper.eq("is_enable_spec", goods.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(goods.getIsDelete())) {
                queryWrapper.eq("is_delete", goods.getIsDelete());
            }
        }
        return queryWrapper;
    }


    /**
     * 修改Goods
     *
     * @param goods
     */
    @Override
    public void update(Goods goods) {
        this.updateById(goods);
    }


    /**
     * 增加Goods
     *
     * @param goodsEntity
     */
    @Override
    public void add(GoodsEntity goodsEntity) {
        Goods goods = goodsEntity.getGoods();
        goods.setAuditStatus("0");
        goods.setIsDelete("0");
        this.save(goods);
        GoodsDesc goodsDesc = goodsEntity.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId());
        goodsDescMapper.insert(goodsDesc);
        if ("1".equals(goods.getIsEnableSpec())) {

            List<Item> itemList = goodsEntity.getItemList();
            itemList.forEach(i -> {
                String itemName = goods.getGoodsName() + " ";
                String spec = i.getSpec();
                Map<String, String> map = JSON.parseObject(spec, Map.class);
                Set<Map.Entry<String, String>> entries = map.entrySet();
                for (Map.Entry<String, String> m : entries) {
                    itemName += m.getValue() + " ";
                }

                i.setTitle(itemName);
                i.setCreateTime(new Date());
                i.setUpdateTime(new Date());
                i.setGoodsId(goods.getId());
                i.setSellerId(goods.getSellerId());
                i.setCategoryId(goods.getCategory3Id());
                i.setBrand(brandMapper.selectById(goods.getBrandId()).getName());
                i.setSeller(sellerMapper.selectById(goods.getSellerId()).getName());

                List<Map> itemImages = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
                if (itemImages != null && itemImages.size() > 0) {
                    i.setImage((String) itemImages.get(0).get("url"));
                }

                itemMapper.insert(i);

            });

        } else {
            //不启用规格  SKU信息为默认值
            Item item = new Item();
            item.setTitle(goodsEntity.getGoods().getGoodsName());
            item.setPrice(goodsEntity.getGoods().getPrice());
            item.setNum(9999);
            item.setStatus("1");
            item.setIsDefault("1");
            item.setSpec("{}");

            item.setGoodsId(goodsEntity.getGoods().getId());

            itemMapper.insert(item);
        }

    }

    /**
     * 根据ID查询Goods
     *
     * @param id
     * @return
     */
    @Override
    public GoodsEntity findById(Long id) {
        GoodsEntity goodsEntity = new GoodsEntity();
        Goods goods = this.getById(id);
        GoodsDesc goodsDesc = goodsDescMapper.selectById(id);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", id);
        List<Item> items = itemMapper.selectList(queryWrapper);
        goodsEntity.setGoods(goods);
        goodsEntity.setGoodsDesc(goodsDesc);
        goodsEntity.setItemList(items);
        return goodsEntity;
    }

    /**
     * 查询Goods全部数据
     *
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return this.list(new QueryWrapper<Goods>());
    }

    /**
     * 商品审核
     *
     * @param goodsId
     */
    @Override
    public void audit(Long goodsId) {
        Goods goods = this.getById(goodsId);
        if (goods == null) {
            throw new RuntimeException("该商品不存在!");
        }

        if ("1".equals(goods.getIsDelete())) {
            throw new RuntimeException("该商品已删除!");
        }

        goods.setAuditStatus("1");
        goods.setIsMarketable("1");

        this.update(goods);
    }

    /**
     * 下架商品
     *
     * @param goodsId
     */
    @Override
    public void pull(Long goodsId) {
        Goods goods = this.getById(goodsId);
        if (goods == null) {
            throw new RuntimeException("该商品不存在!");
        }

        if ("1".equals(goods.getIsDelete())) {
            throw new RuntimeException("该商品已删除!");
        }

        if ("0".equals(goods.getAuditStatus())) {
            throw new RuntimeException("该商品未审核,不能进行下架处理");
        }

        if ("0".equals(goods.getIsMarketable())) {
            throw new RuntimeException("该商品已下架,请不要重复操作!");
        }

        goods.setIsMarketable("0");
        this.update(goods);
    }


    /**
     * 商品上架
     *
     * @param goodsId
     */
    @Override
    public void put(Long goodsId) {
        Goods goods = this.getById(goodsId);
        if (goods == null) {
            throw new RuntimeException("该商品不存在!");
        }

        if ("1".equals(goods.getIsDelete())) {
            throw new RuntimeException("该商品已删除!");
        }

        if ("0".equals(goods.getAuditStatus())) {
            throw new RuntimeException("该商品未审核,不能进行操作");
        }

        if ("1".equals(goods.getIsMarketable())) {
            throw new RuntimeException("该商品已上架,请不要重复操作!");
        }

        goods.setIsMarketable("1");
        this.update(goods);
    }

    /**
     * 批量商品下架
     *
     * @param goodsIds
     * @return
     */
    @Override
    public int pullMany(Long[] goodsIds) {
        Goods goods = new Goods();
        goods.setIsMarketable("0");

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", goodsIds)
                .eq("is_delete", "0")
                .eq("audit_status", "1")
                .eq("is_marketable", "1");

        int update = goodsMapper.update(goods, queryWrapper);

        return update;


    }

    /**
     * 批量商品上架
     *
     * @param goodsIds
     * @return
     */
    @Override
    public int putMany(Long[] goodsIds) {
        Goods goods = new Goods();
        goods.setIsMarketable("1");

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", goodsIds)
                .eq("is_delete", "0")
                .eq("audit_status", "1")
                .eq("is_marketable", "0");

        int update = goodsMapper.update(goods, queryWrapper);
        return update;
    }

    /**
     * 删除
     *
     * @param goodsId
     */
    @Override
    public void delete(Long goodsId) {

        Goods goods = this.getById(goodsId);
        if (goods == null) {
            throw new RuntimeException("该商品不存在");
        }

        if ("1".equals(goods.getIsMarketable())) {
            throw new RuntimeException("该商品处于上架状态,请下架 后再删除");
        }

        goods.setIsDelete("1");
        goods.setAuditStatus("0");
        this.update(goods);
    }
}
