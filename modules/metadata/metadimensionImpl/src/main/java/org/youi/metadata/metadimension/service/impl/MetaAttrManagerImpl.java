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

import org.youi.metadata.metadimension.entity.MetaAttr;
import org.youi.metadata.metadimension.mongo.MetaAttrDao;
import org.youi.metadata.metadimension.service.MetaAttrManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("metaAttrManager")
public class MetaAttrManagerImpl implements MetaAttrManager{

    @Autowired(required = false)
	private MetaAttrDao metaAttrDao;

    /**
     * setter
     * @param metaAttrDao
     */
    public void setMetaAttrDao(MetaAttrDao metaAttrDao) {
        this.metaAttrDao = metaAttrDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001090101",caption="列表时间属性")
    @Override
    public List<MetaAttr> getMetaAttrs(
    	@ConditionCollection(domainClazz=MetaAttr.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return metaAttrDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001090102",caption="主键查询时间属性")
    @Override
    public MetaAttr getMetaAttr(@ServiceParam(name="id") String id) {
    	return metaAttrDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001090103",caption="分页查询时间属性")
    @Override
	public PagerRecords getPagerMetaAttrs(Pager pager,//分页条件
			@ConditionCollection(domainClazz=MetaAttr.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = metaAttrDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001090104",caption="保存时间属性")
    @Override
    public MetaAttr saveMetaAttr(MetaAttr o){
//    	String metaAttrId = o.getMetaAttrId();
//    	boolean isUpdate = StringUtils.isNotEmpty(metaAttrId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return metaAttrDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001090105",caption="主键删除时间属性")
    @Override
    public void removeMetaAttr(@ServiceParam(name="id") String id){
    	metaAttrDao.remove(id);
    }
}
