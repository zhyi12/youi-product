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

import org.youi.framework.util.StringUtils;
import org.youi.metadata.dictionary.entity.DataResource;
import org.youi.metadata.dictionary.mongo.DataResourceDao;
import org.youi.metadata.dictionary.service.DataResourceManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataResourceManager")
public class DataResourceManagerImpl implements DataResourceManager{

    @Autowired(required = false)
	private DataResourceDao dataResourceDao;

    /**
     * setter
     * @param dataResourceDao
     */
    public void setDataResourceDao(DataResourceDao dataResourceDao) {
        this.dataResourceDao = dataResourceDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001030201",caption="列表数据资源")
    @Override
    public List<DataResource> getDataResources(
    	@ConditionCollection(domainClazz=DataResource.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataResourceDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030202",caption="主键查询数据资源")
    @Override
    public DataResource getDataResource(@ServiceParam(name="id") String id) {
    	return dataResourceDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001030203",caption="分页查询数据资源")
    @Override
	public PagerRecords getPagerDataResources(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataResource.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataResourceDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001030204",caption="保存数据资源")
    @Override
    public DataResource saveDataResource(DataResource o){
    	String dataResourceId = o.getId();
    	boolean isUpdate = StringUtils.isNotEmpty(dataResourceId);
    	if(isUpdate){//修改

    	}else{//新增
            o = o.buildKey();
    	}

    	return dataResourceDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001030205",caption="主键删除数据资源")
    @Override
    public void removeDataResource(@ServiceParam(name="id") String id){
    	dataResourceDao.remove(id);
    }

    public List<DataResource> findByDataResourceIdIn(String[] dataResourceIds){
        return dataResourceDao.findByIdIn(dataResourceIds);
    }
}
