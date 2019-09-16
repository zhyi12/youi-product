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
package org.youi.agency.service.impl;

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

import org.youi.agency.entity.Agency;
import org.youi.agency.mongo.AgencyDao;
import org.youi.agency.service.AgencyManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("agencyManager")
public class AgencyManagerImpl implements AgencyManager{

    @Autowired(required = false)
	private AgencyDao agencyDao;

    /**
     * setter
     * @param agencyDao
     */
    public void setAgencyDao(AgencyDao agencyDao) {
        this.agencyDao = agencyDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001010101",caption="列表机构")
    @Override
    public List<Agency> getAgencys(
    	@ConditionCollection(domainClazz=Agency.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return agencyDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001010102",caption="主键查询机构")
    @Override
    public Agency getAgency(@ServiceParam(name="id") String id) {
    	return agencyDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001010103",caption="分页查询机构")
    @Override
	public PagerRecords getPagerAgencys(Pager pager,//分页条件
			@ConditionCollection(domainClazz=Agency.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = agencyDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001010104",caption="保存机构")
    @Override
    public Agency saveAgency(Agency o){
//    	String agencyId = o.getAgencyId();
//    	boolean isUpdate = StringUtils.isNotEmpty(agencyId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return agencyDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001010105",caption="主键删除机构")
    @Override
    public void removeAgency(@ServiceParam(name="id") String id){
    	agencyDao.remove(id);
    }
}
