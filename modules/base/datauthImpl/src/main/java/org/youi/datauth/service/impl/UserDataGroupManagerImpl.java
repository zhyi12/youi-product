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

import org.youi.datauth.entity.UserDataGroup;
import org.youi.datauth.mongo.UserDataGroupDao;
import org.youi.datauth.service.UserDataGroupManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("userDataGroupManager")
public class UserDataGroupManagerImpl implements UserDataGroupManager{

    @Autowired(required = false)
	private UserDataGroupDao userDataGroupDao;

    /**
     * setter
     * @param userDataGroupDao
     */
    public void setUserDataGroupDao(UserDataGroupDao userDataGroupDao) {
        this.userDataGroupDao = userDataGroupDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001020301",caption="列表用户数据权限组")
    @Override
    public List<UserDataGroup> getUserDataGroups(
    	@ConditionCollection(domainClazz=UserDataGroup.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return userDataGroupDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020302",caption="主键查询用户数据权限组")
    @Override
    public UserDataGroup getUserDataGroup(@ServiceParam(name="id") String id) {
    	return userDataGroupDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001020303",caption="分页查询用户数据权限组")
    @Override
	public PagerRecords getPagerUserDataGroups(Pager pager,//分页条件
			@ConditionCollection(domainClazz=UserDataGroup.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = userDataGroupDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001020304",caption="保存用户数据权限组")
    @Override
    public UserDataGroup saveUserDataGroup(UserDataGroup o){
//    	String userDataGroupId = o.getUserDataGroupId();
//    	boolean isUpdate = StringUtils.isNotEmpty(userDataGroupId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return userDataGroupDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001020305",caption="主键删除用户数据权限组")
    @Override
    public void removeUserDataGroup(@ServiceParam(name="id") String id){
    	userDataGroupDao.remove(id);
    }
}
