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

import org.youi.metadata.metadimension.entity.MetaCategory;
import org.youi.metadata.metadimension.mongo.MetaCategoryDao;
import org.youi.metadata.metadimension.service.MetaCategoryManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("metaCategoryManager")
public class MetaCategoryManagerImpl implements MetaCategoryManager{

    @Autowired(required = false)
	private MetaCategoryDao metaCategoryDao;

    /**
     * setter
     * @param metaCategoryDao
     */
    public void setMetaCategoryDao(MetaCategoryDao metaCategoryDao) {
        this.metaCategoryDao = metaCategoryDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001090201",caption="列表分类属性")
    @Override
    public List<MetaCategory> getMetaCategorys(
    	@ConditionCollection(domainClazz=MetaCategory.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return metaCategoryDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001090202",caption="主键查询分类属性")
    @Override
    public MetaCategory getMetaCategory(@ServiceParam(name="id") String id) {
    	return metaCategoryDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001090203",caption="分页查询分类属性")
    @Override
	public PagerRecords getPagerMetaCategorys(Pager pager,//分页条件
			@ConditionCollection(domainClazz=MetaCategory.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = metaCategoryDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001090204",caption="保存分类属性")
    @Override
    public MetaCategory saveMetaCategory(MetaCategory o){
//    	String metaCategoryId = o.getMetaCategoryId();
//    	boolean isUpdate = StringUtils.isNotEmpty(metaCategoryId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return metaCategoryDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001090205",caption="主键删除分类属性")
    @Override
    public void removeMetaCategory(@ServiceParam(name="id") String id){
    	metaCategoryDao.remove(id);
    }
}
