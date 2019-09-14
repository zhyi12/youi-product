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

import org.youi.metadata.dictionary.entity.DataTableColumn;
import org.youi.metadata.dictionary.mongo.DataTableColumnDao;
import org.youi.metadata.dictionary.service.DataTableColumnManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataTableColumnManager")
public class DataTableColumnManagerImpl implements DataTableColumnManager{

    @Autowired(required = false)
	private DataTableColumnDao dataTableColumnDao;

    /**
     * setter
     * @param dataTableColumnDao
     */
    public void setDataTableColumnDao(DataTableColumnDao dataTableColumnDao) {
        this.dataTableColumnDao = dataTableColumnDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001030401",caption="列表数据列")
    @Override
    public List<DataTableColumn> getDataTableColumns(
    	@ConditionCollection(domainClazz=DataTableColumn.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataTableColumnDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030402",caption="主键查询数据列")
    @Override
    public DataTableColumn getDataTableColumn(@ServiceParam(name="id") String id) {
    	return dataTableColumnDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001030403",caption="分页查询数据列")
    @Override
	public PagerRecords getPagerDataTableColumns(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataTableColumn.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataTableColumnDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001030404",caption="保存数据列")
    @Override
    public DataTableColumn saveDataTableColumn(DataTableColumn o){
//    	String dataTableColumnId = o.getDataTableColumnId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataTableColumnId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataTableColumnDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001030405",caption="主键删除数据列")
    @Override
    public void removeDataTableColumn(@ServiceParam(name="id") String id){
    	dataTableColumnDao.remove(id);
    }
}
