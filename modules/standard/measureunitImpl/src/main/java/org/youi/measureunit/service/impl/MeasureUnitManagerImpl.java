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
package org.youi.measureunit.service.impl;

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

import org.youi.measureunit.entity.MeasureUnit;
import org.youi.measureunit.mongo.MeasureUnitDao;
import org.youi.measureunit.service.MeasureUnitManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("measureUnitManager")
public class MeasureUnitManagerImpl implements MeasureUnitManager{

    @Autowired(required = false)
	private MeasureUnitDao measureUnitDao;

    /**
     * setter
     * @param measureUnitDao
     */
    public void setMeasureUnitDao(MeasureUnitDao measureUnitDao) {
        this.measureUnitDao = measureUnitDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8004020101",caption="列表计量单位")
    @Override
    public List<MeasureUnit> getMeasureUnits(
    	@ConditionCollection(domainClazz=MeasureUnit.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return measureUnitDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8004020102",caption="主键查询计量单位")
    @Override
    public MeasureUnit getMeasureUnit(@ServiceParam(name="id") String id) {
    	return measureUnitDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8004020103",caption="分页查询计量单位")
    @Override
	public PagerRecords getPagerMeasureUnits(Pager pager,//分页条件
			@ConditionCollection(domainClazz=MeasureUnit.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = measureUnitDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8004020104",caption="保存计量单位")
    @Override
    public MeasureUnit saveMeasureUnit(MeasureUnit o){
//    	String measureUnitId = o.getMeasureUnitId();
//    	boolean isUpdate = StringUtils.isNotEmpty(measureUnitId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return measureUnitDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8004020105",caption="主键删除计量单位")
    @Override
    public void removeMeasureUnit(@ServiceParam(name="id") String id){
    	measureUnitDao.remove(id);
    }


    @EsbServiceMapping(trancode="8004020106",caption="检索计量单位")
    public List<MeasureUnit> searchByTerm(@ServiceParam(name = "term") String term){
        return measureUnitDao.findStartByTermOnAOrB("unitCaption","id",term);
    }
}
