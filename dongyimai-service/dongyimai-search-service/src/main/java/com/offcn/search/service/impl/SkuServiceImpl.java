package com.offcn.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.search.dao.SkuEsMapper;
import com.offcn.search.pojo.SkuInfo;
import com.offcn.search.service.SkuService;
import com.offcn.sellergoods.feign.ItemFeign;
import com.offcn.sellergoods.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/10 22:36
 * @description
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ItemFeign itemFeign;

    @Override
    public void importSku() {

        Result<List<Item>> result = itemFeign.findByStatus("1");
        String jsonString = JSON.toJSONString(result.getData());
        List<SkuInfo> skuInfos = JSON.parseArray(jsonString, SkuInfo.class);

        skuInfos.forEach(s -> {
            String spec = s.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            s.setSpecMap(map);
        });

        skuEsMapper.saveAll(skuInfos);
    }
}
