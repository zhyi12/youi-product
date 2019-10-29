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
package org.youi.datauth.service.impl;

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

import org.youi.datauth.entity.DataGroup;
import org.youi.datauth.mongo.DataGroupDao;
import org.youi.datauth.service.DataGroupManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataGroupManager")
public class DataGroupManagerImpl implements DataGroupManager{

    @Autowired(required = false)
	private DataGroupDao dataGroupDao;

    /**
     * setter
     * @param dataGroupDao
     */
    public void setDataGroupDao(DataGroupDao dataGroupDao) {
        this.dataGroupDao = dataGroupDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001020101",caption="列表数据权限组")
    @Override
    public List<DataGroup> getDataGroups(
    	@ConditionCollection(domainClazz=DataGroup.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataGroupDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020102",caption="主键查询数据权限组")
    @Override
    public DataGroup getDataGroup(@ServiceParam(name="id") String id) {
    	return dataGroupDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001020103",caption="分页查询数据权限组")
    @Override
	public PagerRecords getPagerDataGroups(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataGroup.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataGroupDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001020104",caption="保存数据权限组")
    @Override
    public DataGroup saveDataGroup(DataGroup o){
//    	String dataGroupId = o.getDataGroupId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataGroupId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataGroupDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001020105",caption="主键删除数据权限组")
    @Override
    public void removeDataGroup(@ServiceParam(name="id") String id){
    	dataGroupDao.remove(id);
    }
}
