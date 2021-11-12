package com.offcn.search.service.impl;

import com.alibaba.druid.sql.visitor.functions.Trim;
import com.alibaba.fastjson.JSON;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.search.dao.SkuEsMapper;
import com.offcn.search.pojo.SkuInfo;
import com.offcn.search.service.SkuService;
import com.offcn.sellergoods.feign.ItemFeign;
import com.offcn.sellergoods.pojo.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

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

    @Override
    public Map search(Map<String, Object> searchMap) {

        Map<String, Object> resultMap = new HashMap<>();
        Object keywords = searchMap.get("keywords");

        if (keywords == null) {
            keywords = "联想";
        }

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        builder.withQuery(QueryBuilders.matchQuery("title", keywords));


        //排序
        Object sortRule = searchMap.get("sortRule");
        Object sortFiled = searchMap.get("sortField");
        if (!StringUtils.isEmpty(sortFiled) && !StringUtils.isEmpty(sortRule)){
            builder.withSort(SortBuilders.fieldSort(sortFiled+"").order(sortRule.equals("DESC")? SortOrder.DESC:SortOrder.ASC));
        }

        //分页功能
        Object currentPage = searchMap.get("currentPage");
        if (currentPage == null || (int)currentPage < 1) {
            currentPage = 1;
        }
        Object pageSize = searchMap.get("pageSize");
        if (pageSize == null || (int) pageSize < 1) {
            pageSize = 2;
        }

        builder.withPageable(PageRequest.of((int) currentPage-1, (int) pageSize));


        //设置高亮条件
        builder.withHighlightFields(new HighlightBuilder.Field("title"));
        builder.withHighlightBuilder(
                new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>")
        );

        //过滤查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        Object category = searchMap.get("category");
        if (category != null) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("category", category));
        }

        Object brand = searchMap.get("brand");
        if (brand != null) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("brand", brand));
        }

        if (searchMap != null) {
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    List specList = (List) searchMap.get(key);
                    String spec = key.substring(5);
                    boolQueryBuilder.filter(QueryBuilders.termsQuery("specMap." + spec + ".keyword", specList));

                }
            }
        }

        Object priceRange = searchMap.get("priceRange");
        if (priceRange != null) {
            String[] prices = (priceRange + "").split("-");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(prices[0]));
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(prices[1]));
        }

        builder.withFilter(boolQueryBuilder);


        builder.addAggregation(AggregationBuilders.terms("skuCategorygroup").field("category"))
                .addAggregation(AggregationBuilders.terms("skuBrandgroup").field("brand"))
                .addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(100))
                .addAggregation(AggregationBuilders.max("maxPrice").field("price"))
                .addAggregation(AggregationBuilders.min("minPrice").field("price"));


        NativeSearchQuery query = builder.build();

        SearchHits<SkuInfo> searchHits = elasticsearchRestTemplate.search(query, SkuInfo.class);

        SearchPage<SkuInfo> searchPage = SearchHitSupport.searchPageFor(searchHits, query.getPageable());


        Terms terms = searchHits.getAggregations().get("skuCategorygroup");
        List<String> categoryList = new ArrayList<>();
        if (terms != null) {
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String categoryName = bucket.getKeyAsString();
                categoryList.add(categoryName);
            }

        }

        Terms brandTrems = searchHits.getAggregations().get("skuBrandgroup");
        List<String> brandList = new ArrayList<>();
        if (brandTrems != null) {

            for (Terms.Bucket bucket : brandTrems.getBuckets()) {
                String brandStr = bucket.getKeyAsString();
                brandList.add(brandStr);

            }

        }

        Max maxPrice = searchHits.getAggregations().get("maxPrice");
        Min minPrice = searchHits.getAggregations().get("minPrice");

        List<String> priceRangeList = this.getPriceRangeList((int) maxPrice.getValue(), (int) minPrice.getValue(), 5);


        //第一种获取规格列表
        Terms specTerms = searchHits.getAggregations().get("skuSpecgroup");
        Map<String, Set> specMap = new HashMap<>();
        for (Terms.Bucket bucket : specTerms.getBuckets()) {
            String spec = bucket.getKeyAsString();
            Map<String, Object> map = JSON.parseObject(spec, Map.class);
            for (String key : map.keySet()) {
                Set value = specMap.get(key);
                if (value == null) {
                    value = new HashSet();
                }
                value.add(map.get(key));
                specMap.put(key, value);
            }
        }



        List<SkuInfo> skuInfoList = new ArrayList<>();

        //Map<String, Set> specMap = new HashMap<>();


        for (SearchHit<SkuInfo> searchHit : searchPage.getContent()) {
            SkuInfo skuInfo = searchHit.getContent();
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            if (highlightFields != null){
                skuInfo.setTitle(highlightFields.get("title").get(0));
            }

            skuInfoList.add(skuInfo);
            //第二种获取规格列表
            //Map<String,Object> map = JSON.parseObject(skuInfo.getSpec(), Map.class);
            //for (String key : map.keySet()) {
            //    Set value = specMap.get(key);
            //    if (value == null){
            //        value = new HashSet();
            //    }
            //    value.add(map.get(key));
            //    specMap.put(key, value);
            //}



        }



        resultMap.put("rows", skuInfoList);
        resultMap.put("total", searchHits.getTotalHits());
        resultMap.put("totalPages", searchPage.getTotalPages());
        resultMap.put("categoryList", categoryList);
        resultMap.put("brandList", brandList);
        resultMap.put("specMap", specMap);
        resultMap.put("priceRangeList", priceRangeList);

        return resultMap;
    }

    @Override
    public void clearAll() {
        skuEsMapper.deleteAll();
    }


    public List<String> getPriceRangeList(int max, int min, int count) {
        int difference = max - min;
        List<String> priceRange = new ArrayList<>();
        int value = difference / count;
        for (int i = 0; i < count; i++) {
            if (i != (count - 1)) {
                priceRange.add(min + "-" + (min += value));
            } else {
                priceRange.add(min + "-" + max);
            }
        }
        return priceRange;
    }


}
