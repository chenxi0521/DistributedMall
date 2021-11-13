package com.offcn.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.offcn.itempage.feign.PageFeign;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/13 11:05
 * @description
 */
@CanalEventListener
public class CanalGoodsDescListenter {


    @Autowired
    private PageFeign pageFeign;

    @Value("${pagepath}")
    private String pagepath;


    @ListenPoint(
            destination = "example",
            schema = "dongyimaidb",
            table = {"tb_goods_desc"},
            eventType = {
                    CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT
            }

    )
    public void onEventCustomSpuDesc(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        Long goodsId = getGoodsDescId(eventType, rowData);
        if (eventType != CanalEntry.EventType.DELETE){
            if (goodsId != null){
                pageFeign.createPageHtml(goodsId);
            }

        }else {
            File file = new File(pagepath,goodsId+".html");
            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                System.gc();//启动jvm垃圾回收
                file.delete();
            }
        }

    }


    public Long getGoodsDescId(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        if (eventType == CanalEntry.EventType.DELETE){

            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            for (CanalEntry.Column column : beforeColumnsList) {
                if (column.getName().equalsIgnoreCase("goods_id")) {
                    String value = column.getValue();
                    return Long.parseLong(value);
                }

            }
        }else {
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            for (CanalEntry.Column column : afterColumnsList) {
                if (column.getName().equalsIgnoreCase("goods_id")) {
                    String value = column.getValue();
                    return Long.parseLong(value);
                }

            }
        }
        return null;
    }
}
