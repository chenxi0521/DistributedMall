package com.offcn.sellergoods.group;

import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.pojo.GoodsDesc;
import com.offcn.sellergoods.pojo.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/8 21:42
 * @description
 */
@ApiModel(description = "商品复合实体类",value = "GoodsEntity")
public class GoodsEntity implements Serializable {

    @ApiModelProperty(value = "商品实体类",required = false)
    private Goods goods;

    @ApiModelProperty(value = "商品详情实体类",required = false)
    private GoodsDesc goodsDesc;

    @ApiModelProperty(value = "sku数据",required = false)
    private List<Item> itemList;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
