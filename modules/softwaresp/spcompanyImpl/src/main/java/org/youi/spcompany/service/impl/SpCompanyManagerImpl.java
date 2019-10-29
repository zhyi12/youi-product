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
package org.youi.spcompany.service.impl;

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

import org.youi.spcompany.entity.SpCompany;
import org.youi.spcompany.mongo.SpCompanyDao;
import org.youi.spcompany.service.SpCompanyManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("spCompanyManager")
public class SpCompanyManagerImpl implements SpCompanyManager{

    @Autowired(required = false)
	private SpCompanyDao spCompanyDao;

    /**
     * setter
     * @param spCompanyDao
     */
    public void setSpCompanyDao(SpCompanyDao spCompanyDao) {
        this.spCompanyDao = spCompanyDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="7001010101",caption="列表软件开发商")
    @Override
    public List<SpCompany> getSpCompanys(
    	@ConditionCollection(domainClazz=SpCompany.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return spCompanyDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="7001010102",caption="主键查询软件开发商")
    @Override
    public SpCompany getSpCompany(@ServiceParam(name="id") String id) {
    	return spCompanyDao.get(id);
    }
	
	@EsbServiceMapping(trancode="7001010103",caption="分页查询软件开发商")
    @Override
	public PagerRecords getPagerSpCompanys(Pager pager,//分页条件
			@ConditionCollection(domainClazz=SpCompany.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = spCompanyDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="7001010104",caption="保存软件开发商")
    @Override
    public SpCompany saveSpCompany(SpCompany o){
//    	String spCompanyId = o.getSpCompanyId();
//    	boolean isUpdate = StringUtils.isNotEmpty(spCompanyId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return spCompanyDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="7001010105",caption="主键删除软件开发商")
    @Override
    public void removeSpCompany(@ServiceParam(name="id") String id){
    	spCompanyDao.remove(id);
    }
}
