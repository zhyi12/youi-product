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
package org.youi.metadata.metadimension.service.impl;

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

import org.youi.metadata.metadimension.entity.MetaCategoryItem;
import org.youi.metadata.metadimension.mongo.MetaCategoryItemDao;
import org.youi.metadata.metadimension.service.MetaCategoryItemManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("metaCategoryItemManager")
public class MetaCategoryItemManagerImpl implements MetaCategoryItemManager{

    @Autowired(required = false)
	private MetaCategoryItemDao metaCategoryItemDao;

    /**
     * setter
     * @param metaCategoryItemDao
     */
    public void setMetaCategoryItemDao(MetaCategoryItemDao metaCategoryItemDao) {
        this.metaCategoryItemDao = metaCategoryItemDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001090301",caption="列表分类项")
    @Override
    public List<MetaCategoryItem> getMetaCategoryItems(
    	@ConditionCollection(domainClazz=MetaCategoryItem.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return metaCategoryItemDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001090302",caption="主键查询分类项")
    @Override
    public MetaCategoryItem getMetaCategoryItem(@ServiceParam(name="id") String id) {
    	return metaCategoryItemDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001090303",caption="分页查询分类项")
    @Override
	public PagerRecords getPagerMetaCategoryItems(Pager pager,//分页条件
			@ConditionCollection(domainClazz=MetaCategoryItem.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = metaCategoryItemDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001090304",caption="保存分类项")
    @Override
    public MetaCategoryItem saveMetaCategoryItem(MetaCategoryItem o){
//    	String metaCategoryItemId = o.getMetaCategoryItemId();
//    	boolean isUpdate = StringUtils.isNotEmpty(metaCategoryItemId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return metaCategoryItemDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001090305",caption="主键删除分类项")
    @Override
    public void removeMetaCategoryItem(@ServiceParam(name="id") String id){
    	metaCategoryItemDao.remove(id);
    }
}
