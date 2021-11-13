package com.offcn.itempage.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.itempage.service.PageService;
import com.offcn.sellergoods.feign.GoodsFeign;
import com.offcn.sellergoods.feign.ItemCatFeign;
import com.offcn.sellergoods.group.GoodsEntity;
import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.pojo.GoodsDesc;
import com.offcn.sellergoods.pojo.Item;
import com.offcn.sellergoods.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/12 16:07
 * @description
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private ItemCatFeign itemCatFeign;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pagepath}")
    private String pagepath;


    @Override
    public void createPageHtml(Long goodsid) {
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(goodsid);
        context.setVariables(dataModel);

        File file = new File(pagepath);
        if (!file.exists()){
            file.mkdirs();
        }

        File pageFile = new File(file, goodsid + ".html");

        try {
            PrintWriter writer = new PrintWriter(pageFile,"UTF-8");
            templateEngine.process("item",context,writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public Map<String, Object> buildDataModel(Long goodsId) {
        Map<String, Object> result = new HashMap<>();
        Result<GoodsEntity> goodsEntityResult = goodsFeign.findById(goodsId);
        GoodsEntity goodsEntity = goodsEntityResult.getData();
        Goods goods = goodsEntity.getGoods();
        GoodsDesc goodsDesc = goodsEntity.getGoodsDesc();
        List<Item> itemList = goodsEntity.getItemList();
        List<Map> specificationItems = JSON.parseArray(goodsDesc.getSpecificationItems(), Map.class);
        List<Map> itemImages = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
        ItemCat category1 = itemCatFeign.findById(goods.getCategory1Id()).getData();
        ItemCat category2 = itemCatFeign.findById(goods.getCategory2Id()).getData();
        ItemCat category3 = itemCatFeign.findById(goods.getCategory3Id()).getData();
        result.put("goods", goods);
        result.put("goodsDesc", goodsDesc);
        result.put("itemList", itemList);
        result.put("specificationItems", specificationItems);
        result.put("itemImages", itemImages);
        result.put("category1", category1);
        result.put("category2", category2);
        result.put("category3", category3);
        return result;
    }
}
