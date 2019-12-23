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
package org.youi.dataworking.workingdata.service.impl;

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

import org.youi.dataworking.workingdata.entity.WorkingData;
import org.youi.dataworking.workingdata.mongo.WorkingDataDao;
import org.youi.dataworking.workingdata.service.WorkingDataManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("workingDataManager")
public class WorkingDataManagerImpl implements WorkingDataManager{

    @Autowired(required = false)
	private WorkingDataDao workingDataDao;

    /**
     * setter
     * @param workingDataDao
     */
    public void setWorkingDataDao(WorkingDataDao workingDataDao) {
        this.workingDataDao = workingDataDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8006010101",caption="列表工作数据")
    @Override
    public List<WorkingData> getWorkingDatas(
    	@ConditionCollection(domainClazz=WorkingData.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return workingDataDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8006010102",caption="主键查询工作数据")
    @Override
    public WorkingData getWorkingData(@ServiceParam(name="id") String id) {
    	return workingDataDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8006010103",caption="分页查询工作数据")
    @Override
	public PagerRecords getPagerWorkingDatas(Pager pager,//分页条件
			@ConditionCollection(domainClazz=WorkingData.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = workingDataDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8006010104",caption="保存工作数据")
    @Override
    public WorkingData saveWorkingData(WorkingData o){
//    	String workingDataId = o.getWorkingDataId();
//    	boolean isUpdate = StringUtils.isNotEmpty(workingDataId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return workingDataDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8006010105",caption="主键删除工作数据")
    @Override
    public void removeWorkingData(@ServiceParam(name="id") String id){
    	workingDataDao.remove(id);
    }
}
