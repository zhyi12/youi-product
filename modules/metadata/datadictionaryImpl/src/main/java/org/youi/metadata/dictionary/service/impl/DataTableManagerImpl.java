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

import org.youi.metadata.dictionary.entity.DataTable;
import org.youi.metadata.dictionary.mongo.DataTableDao;
import org.youi.metadata.dictionary.service.DataTableManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataTableManager")
public class DataTableManagerImpl implements DataTableManager{

    @Autowired(required = false)
	private DataTableDao dataTableDao;

    /**
     * setter
     * @param dataTableDao
     */
    public void setDataTableDao(DataTableDao dataTableDao) {
        this.dataTableDao = dataTableDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001030301",caption="列表数据表")
    @Override
    public List<DataTable> getDataTables(
    	@ConditionCollection(domainClazz=DataTable.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataTableDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030302",caption="主键查询数据表")
    @Override
    public DataTable getDataTable(@ServiceParam(name="id") String id) {
    	return dataTableDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001030303",caption="分页查询数据表")
    @Override
	public PagerRecords getPagerDataTables(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataTable.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataTableDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001030304",caption="保存数据表")
    @Override
    public DataTable saveDataTable(DataTable o){
//    	String dataTableId = o.getDataTableId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataTableId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataTableDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001030305",caption="主键删除数据表")
    @Override
    public void removeDataTable(@ServiceParam(name="id") String id){
    	dataTableDao.remove(id);
    }
}
