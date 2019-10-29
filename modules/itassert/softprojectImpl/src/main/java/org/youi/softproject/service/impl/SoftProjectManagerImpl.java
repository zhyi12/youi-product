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
package org.youi.softproject.service.impl;

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

import org.youi.softproject.entity.SoftProject;
import org.youi.softproject.mongo.SoftProjectDao;
import org.youi.softproject.service.SoftProjectManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("softProjectManager")
public class SoftProjectManagerImpl implements SoftProjectManager{

    @Autowired(required = false)
	private SoftProjectDao softProjectDao;

    /**
     * setter
     * @param softProjectDao
     */
    public void setSoftProjectDao(SoftProjectDao softProjectDao) {
        this.softProjectDao = softProjectDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="7002010101",caption="列表软件项目")
    @Override
    public List<SoftProject> getSoftProjects(
    	@ConditionCollection(domainClazz=SoftProject.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return softProjectDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="7002010102",caption="主键查询软件项目")
    @Override
    public SoftProject getSoftProject(@ServiceParam(name="id") String id) {
    	return softProjectDao.get(id);
    }
	
	@EsbServiceMapping(trancode="7002010103",caption="分页查询软件项目")
    @Override
	public PagerRecords getPagerSoftProjects(Pager pager,//分页条件
			@ConditionCollection(domainClazz=SoftProject.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = softProjectDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="7002010104",caption="保存软件项目")
    @Override
    public SoftProject saveSoftProject(SoftProject o){
//    	String softProjectId = o.getSoftProjectId();
//    	boolean isUpdate = StringUtils.isNotEmpty(softProjectId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return softProjectDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="7002010105",caption="主键删除软件项目")
    @Override
    public void removeSoftProject(@ServiceParam(name="id") String id){
    	softProjectDao.remove(id);
    }
}
