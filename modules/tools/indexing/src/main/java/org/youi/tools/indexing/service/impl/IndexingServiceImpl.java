package org.youi.tools.indexing.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.TextField;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.util.StringUtils;
import org.youi.tools.indexing.entity.IndexResult;
import org.youi.tools.indexing.entity.MatchingItem;
import org.youi.tools.indexing.service.IndexingService;
import org.youi.tools.indexing.util.IndexingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by zhouyi on 2017/6/12.
 */
@Component("indexingService")
public class IndexingServiceImpl implements IndexingService, ApplicationContextAware {

    private String indexingBaseDir;//本地索引存储路径

    private Analyzer analyzer = new SmartChineseAnalyzer();


    public List<MatchingItem> matchingItems(List<Item> items,List<? extends Domain> records,
                                    Function<Domain,List<TextField>> function){

        String indexName = UUID.randomUUID().toString();//索引名称
        //构建索引
        IndexingUtils.buildIndexing(indexingBaseDir,indexName,analyzer,records,function,true);
        //文本检索
        List<MatchingItem> matchingItems = new ArrayList<>();
        for(Item item:items){
            if(StringUtils.isNotEmpty(item.getText())){
                List<IndexResult> results = IndexingUtils.searchIndex(indexingBaseDir,indexName,analyzer,item.getText());

                MatchingItem matchingItem = new MatchingItem(item);
                if(!CollectionUtils.isEmpty(results)){
                    matchingItem.setMappedId(results.get(0).getId());
                    matchingItem.setMatchingResults(results);
                }
                matchingItems.add(matchingItem);
            }
        }
        //删除索引文件
        FileUtils.deleteQuietly(new File(indexingBaseDir + File.separator + indexName));
        return matchingItems;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //临时索引存储路径
        this.initIndexingBaseDir(applicationContext);
    }

    /**
     *
     * @param context
     */
    private void initIndexingBaseDir(ApplicationContext context) {
        File indexingDir = new File(FileUtils.getTempDirectory(),".youiIndexing");
        if(!indexingDir.exists()){
            indexingDir.mkdirs();
        }
        this.indexingBaseDir = indexingDir.getAbsolutePath();
    }
}
