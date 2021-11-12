package com.offcn.search.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author chenxi
 * @date 2021/11/10 22:35
 * @description
 */

public interface SkuService {

    void importSku();

    /**
     * 搜索功能
     * @param searchMap
     * @return
     */
    Map search(Map<String,Object> searchMap);

    /**
     * 清空es
     */
    void clearAll();
}
