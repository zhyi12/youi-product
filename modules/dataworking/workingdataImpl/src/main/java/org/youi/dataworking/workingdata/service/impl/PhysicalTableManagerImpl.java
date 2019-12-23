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

import org.youi.dataworking.workingdata.entity.PhysicalTable;
import org.youi.dataworking.workingdata.mongo.PhysicalTableDao;
import org.youi.dataworking.workingdata.service.PhysicalTableManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("physicalTableManager")
public class PhysicalTableManagerImpl implements PhysicalTableManager{

    @Autowired(required = false)
	private PhysicalTableDao physicalTableDao;

    /**
     * setter
     * @param physicalTableDao
     */
    public void setPhysicalTableDao(PhysicalTableDao physicalTableDao) {
        this.physicalTableDao = physicalTableDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8006010201",caption="列表物理表")
    @Override
    public List<PhysicalTable> getPhysicalTables(
    	@ConditionCollection(domainClazz=PhysicalTable.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return physicalTableDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8006010202",caption="主键查询物理表")
    @Override
    public PhysicalTable getPhysicalTable(@ServiceParam(name="id") String id) {
    	return physicalTableDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8006010203",caption="分页查询物理表")
    @Override
	public PagerRecords getPagerPhysicalTables(Pager pager,//分页条件
			@ConditionCollection(domainClazz=PhysicalTable.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = physicalTableDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8006010204",caption="保存物理表")
    @Override
    public PhysicalTable savePhysicalTable(PhysicalTable o){
//    	String physicalTableId = o.getPhysicalTableId();
//    	boolean isUpdate = StringUtils.isNotEmpty(physicalTableId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return physicalTableDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8006010205",caption="主键删除物理表")
    @Override
    public void removePhysicalTable(@ServiceParam(name="id") String id){
    	physicalTableDao.remove(id);
    }
}
