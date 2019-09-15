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
package org.youi.dataquery.query.service.impl;

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

import org.youi.dataquery.query.entity.DataQuery;
import org.youi.dataquery.query.mongo.DataQueryDao;
import org.youi.dataquery.query.service.DataQueryManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataQueryManager")
public class DataQueryManagerImpl implements DataQueryManager{

    @Autowired(required = false)
	private DataQueryDao dataQueryDao;

    /**
     * setter
     * @param dataQueryDao
     */
    public void setDataQueryDao(DataQueryDao dataQueryDao) {
        this.dataQueryDao = dataQueryDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8002010101",caption="列表数据查询")
    @Override
    public List<DataQuery> getDataQuerys(
    	@ConditionCollection(domainClazz=DataQuery.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataQueryDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8002010102",caption="主键查询数据查询")
    @Override
    public DataQuery getDataQuery(@ServiceParam(name="id") String id) {
    	return dataQueryDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8002010103",caption="分页查询数据查询")
    @Override
	public PagerRecords getPagerDataQuerys(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataQuery.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataQueryDao.complexFindByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8002010104",caption="保存数据查询")
    @Override
    public DataQuery saveDataQuery(DataQuery o){
//    	String dataQueryId = o.getDataQueryId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataQueryId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataQueryDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8002010105",caption="主键删除数据查询")
    @Override
    public void removeDataQuery(@ServiceParam(name="id") String id){
    	dataQueryDao.remove(id);
    }
}
