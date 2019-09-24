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
package org.youi.server.config.service.impl;

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

import org.youi.server.config.entity.ServerProperty;
import org.youi.server.config.mongo.ServerPropertyDao;
import org.youi.server.config.service.ServerPropertyManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("serverPropertyManager")
public class ServerPropertyManagerImpl implements ServerPropertyManager{

    @Autowired(required = false)
	private ServerPropertyDao serverPropertyDao;

    /**
     * setter
     * @param serverPropertyDao
     */
    public void setServerPropertyDao(ServerPropertyDao serverPropertyDao) {
        this.serverPropertyDao = serverPropertyDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9003010101",caption="列表服务参数")
    @Override
    public List<ServerProperty> getServerProperties(
    	@ConditionCollection(domainClazz=ServerProperty.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return serverPropertyDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9003010102",caption="主键查询服务参数")
    @Override
    public ServerProperty getServerProperty(@ServiceParam(name="id") String id) {
    	return serverPropertyDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9003010103",caption="分页查询服务参数")
    @Override
	public PagerRecords getPagerServerProperties(Pager pager,//分页条件
			@ConditionCollection(domainClazz=ServerProperty.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = serverPropertyDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9003010104",caption="保存服务参数")
    @Override
    public ServerProperty saveServerProperty(ServerProperty o){
//    	String serverPropertyId = o.getServerPropertyId();
//    	boolean isUpdate = StringUtils.isNotEmpty(serverPropertyId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return serverPropertyDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9003010105",caption="主键删除服务参数")
    @Override
    public void removeServerProperty(@ServiceParam(name="id") String id){
    	serverPropertyDao.remove(id);
    }
}
