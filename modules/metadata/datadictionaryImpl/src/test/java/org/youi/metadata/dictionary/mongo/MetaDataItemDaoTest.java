package org.youi.metadata.dictionary.mongo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.metadata.dictionary.entity.MetaDataItem;
import org.youi.rowdata.common.model.BatchResult;
import org.youi.rowdata.common.util.RowDataUtils;
import org.youi.rowdata.xls.XlsRowFileExecutor;
import org.youi.tools.indexing.entity.IndexResult;
import org.youi.tools.indexing.entity.MatchingItem;
import org.youi.tools.indexing.service.IndexingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouyi on 2019/9/25.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class MetaDataItemDaoTest {

    @Value("classpath:dic1.xlsx")
    private Resource xlsResource;

    @Autowired
    private XlsRowFileExecutor<MetaDataItem> xlsRowFileExecutor;

    @Autowired
    private MetaDataItemDao metaDataItemDao;

    @Autowired
    private IndexingService indexingService;

    //xls文件中列对应的属性
    private String[] properties = {
        "","folderId","code","text","oldName","name","description","dataType","dataFormat",
            "convert","version","updateTime","updateDescription","updateOperator",
            "owner","grade","scope","accordingTo"
    };

    /**
     * 通过xls文件加载数据
     */
    @Test
    public void importMetaDataItems(){
        try{
            xlsRowFileExecutor.processFile(xlsResource.getFile(),rowResult -> {
                MetaDataItem metaDataItem = RowDataUtils.mapObject(properties,rowResult.getRowData(),MetaDataItem.class);
                metaDataItem.setId(metaDataItem.getCode());
                return metaDataItem;
            },(records)->{
                BatchResult batchResult = new BatchResult();
                //保存该批次数据到数据库
                metaDataItemDao.saveAll(records);
                return batchResult;
            });
        }catch (IOException e){
            //
        }
    }

    @Test
    public void matchItems(){
        List<MetaDataItem> results = metaDataItemDao.commonQuery(null, null);

        System.out.println(results);
    }

}