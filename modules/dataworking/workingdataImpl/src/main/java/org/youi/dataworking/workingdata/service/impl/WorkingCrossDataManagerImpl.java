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

import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.dataworking.workingdata.mongo.WorkingCrossDataDao;
import org.youi.dataworking.workingdata.service.WorkingCrossDataManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("workingCrossDataManager")
public class WorkingCrossDataManagerImpl implements WorkingCrossDataManager{

    @Autowired(required = false)
	private WorkingCrossDataDao workingCrossDataDao;

    /**
     * setter
     * @param workingCrossDataDao
     */
    public void setWorkingCrossDataDao(WorkingCrossDataDao workingCrossDataDao) {
        this.workingCrossDataDao = workingCrossDataDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8006010301",caption="列表交叉表数据")
    @Override
    public List<WorkingCrossData> getWorkingCrossDatas(
    	@ConditionCollection(domainClazz=WorkingCrossData.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return workingCrossDataDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8006010302",caption="主键查询交叉表数据")
    @Override
    public WorkingCrossData getWorkingCrossData(@ServiceParam(name="id") String id) {
    	return workingCrossDataDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8006010303",caption="分页查询交叉表数据")
    @Override
	public PagerRecords getPagerWorkingCrossDatas(Pager pager,//分页条件
			@ConditionCollection(domainClazz=WorkingCrossData.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = workingCrossDataDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8006010304",caption="保存交叉表数据")
    @Override
    public WorkingCrossData saveWorkingCrossData(WorkingCrossData o){
//    	String workingCrossDataId = o.getWorkingCrossDataId();
//    	boolean isUpdate = StringUtils.isNotEmpty(workingCrossDataId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return workingCrossDataDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8006010305",caption="主键删除交叉表数据")
    @Override
    public void removeWorkingCrossData(@ServiceParam(name="id") String id){
    	workingCrossDataDao.remove(id);
    }

    @Override
    public List<WorkingCrossData> saveWorkingCrossDatas(List<WorkingCrossData> workingDatas){
        //TODO 修改批量保存逻辑，防止内存溢出
        return workingCrossDataDao.saveAll(workingDatas);
    }

    @Override
    public List<WorkingCrossData> getWorkingCrossDataByIds(List<String> ids){
        return workingCrossDataDao.findByIdIn(ids);
    }

    public boolean exists(String id){
        return workingCrossDataDao.exists(id);
    }
}
