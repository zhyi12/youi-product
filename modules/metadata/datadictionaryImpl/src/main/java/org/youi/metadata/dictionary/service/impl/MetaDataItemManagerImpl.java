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

import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		PagerRecords pagerRecords = metaDataItemDao.findByPager(pager, conditions, orders);
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
}
