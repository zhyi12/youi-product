/*
 * YOUI框架
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.metadata.dictionary.service.impl;

import java.util.*;

import org.apache.commons.lang.ArrayUtils;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.youi.fileserver.filestore.service.FileClientManager;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.metadata.dictionary.entity.MetaDataItem;
import org.youi.metadata.dictionary.mongo.MetaDataItemDao;
import org.youi.metadata.dictionary.service.MetaDataItemManager;
import org.youi.rowdata.common.model.BatchResult;
import org.youi.tools.indexing.entity.IndexResult;
import org.youi.tools.indexing.entity.MatchingItem;
import org.youi.tools.indexing.service.IndexingService;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("metaDataItemManager")
public class MetaDataItemManagerImpl implements MetaDataItemManager{

    @Autowired(required = false)
	private MetaDataItemDao metaDataItemDao;

    @Autowired(required = false)
    private IndexingService indexingService;//本地索引

    @Autowired(required = false)
    private FileClientManager fileClientManager;//文件客户端

    @Autowired
    private MetaDataItemImporter metaDataItemImporter;

    /**
     * setter
     * @param metaDataItemDao
     */
    public void setMetaDataItemDao(MetaDataItemDao metaDataItemDao) {
        this.metaDataItemDao = metaDataItemDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001030101",caption="列表数据项")
    @Override
    public List<MetaDataItem> getMetaDataItems(
    	@ConditionCollection(domainClazz=MetaDataItem.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return metaDataItemDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030102",caption="主键查询数据项")
    @Override
    public MetaDataItem getMetaDataItem(@ServiceParam(name="id") String id) {
    	return metaDataItemDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001030103",caption="分页查询数据项")
    @Override
	public PagerRecords getPagerMetaDataItems(Pager pager,//分页条件
			@ConditionCollection(domainClazz=MetaDataItem.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = metaDataItemDao.complexFindByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001030104",caption="保存数据项")
    @Override
    public MetaDataItem saveMetaDataItem(MetaDataItem o){
//    	String metaDataItemId = o.getMetaDataItemId();
//    	boolean isUpdate = StringUtils.isNotEmpty(metaDataItemId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return metaDataItemDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001030105",caption="主键删除数据项")
    @Override
    public void removeMetaDataItem(@ServiceParam(name="id") String id){
    	metaDataItemDao.remove(id);
    }

    @EsbServiceMapping(trancode="8001030106",caption="多文本匹配数据项")
    public List<MatchingItem> matchingMetaDataItems(@ServiceParam(name="text")String[] texts){
        List<Item> itemList = new ArrayList<>();
        int index = 1;
        for(String text:texts){
            itemList.add(new Item(Integer.toString(index++),text));
        }
        Map<String,MetaDataItem> fullMatches = findFullMatches(texts);

        List<MetaDataItem> metaDataItems = metaDataItemDao.findAll();
        //检索数据
        List<MatchingItem> matchingItems = indexingService.matchingItems(itemList, metaDataItems, (domain) -> {
            List<TextField> fields = new ArrayList<>();
            MetaDataItem metaDataItem = (MetaDataItem) domain;
            fields.add(new TextField("id", metaDataItem.getName(), Field.Store.YES));
            fields.add(new TextField("text", metaDataItem.getText(), Field.Store.YES));
            fields.add(new TextField("fullText", metaDataItem.getDescription(), Field.Store.YES));
            return fields;
        });
        //补充处理完全匹配的规则，返回完全匹配的项
        matchingItems.forEach(matchingItem -> {
            if(fullMatches.containsKey(matchingItem.getText())){
                MetaDataItem fullMatched = fullMatches.get(matchingItem.getText());
                List<IndexResult> indexResults = new ArrayList<>();
                IndexResult indexResult = new IndexResult();
                indexResult.setId(fullMatched.getName());
                indexResult.setText(matchingItem.getText());
                indexResult.setScore(10f);
                indexResults.add(indexResult);
                matchingItem.setMappedId(fullMatched.getName());
                matchingItem.setText(fullMatched.getText());
                matchingItem.setMatchingResults(indexResults);
            }
        });
        return matchingItems;
    }

    @EsbServiceMapping(trancode="8001030107",caption="检索")
    public List<MetaDataItem> searchByTerm(@ServiceParam(name = "term") String term){
        return metaDataItemDao.findStartByTermOnAOrB("text","name",term);
    }

    @EsbServiceMapping(trancode="8001030108",caption="从xls文件导入数据项")
    public List<BatchResult> importFromXls(@ServiceParam(name = "xlsFileName") String xlsFileName){
        //消费文件，使用后文件自动清理
        return fileClientManager.consumeFile(xlsFileName,(file -> {
            return metaDataItemImporter.fromXls(file);
        }));
    }

    @EsbServiceMapping(trancode="8001030109",caption="根据唯一的name查找数据项")
    public MetaDataItem getMetaDataItemByName(@ServiceParam(name = "name") String name){
        try {
            return metaDataItemDao.findByName(name);
        } catch (IncorrectResultSizeDataAccessException e) {
            //找到多余一条记录
            return metaDataItemDao.findByNameIn(new String[]{name}).get(0);
        }
    }

    /**
     * 根据name集合查询数据项集合
     * @param dataItemIds
     * @return
     */
    public List<MetaDataItem> getMetaDataItemByNames(List<String> dataItemIds){
        return metaDataItemDao.findByNameIn(dataItemIds.toArray(new String[0]));
    }

    /**
     * 从数据使用全文本匹配查询
     * @param texts 匹配的文本集合
     * @return 返回map对象健值为：text，MetaDataItem
     */
    private Map<String,MetaDataItem> findFullMatches(String[] texts) {
        Map<String,MetaDataItem> fullMatches = new HashMap<>();
        //完全匹配
        List<MetaDataItem> metaDataItems = metaDataItemDao.findByTextIn(texts);
        for(MetaDataItem metaDataItem:metaDataItems){
            if(ArrayUtils.contains(texts,metaDataItem.getText())){
                fullMatches.put(metaDataItem.getText(),metaDataItem);
            }
        }
        return fullMatches;
    }
}
