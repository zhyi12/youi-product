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

import org.youi.datauth.entity.UserData;
import org.youi.datauth.mongo.UserDataDao;
import org.youi.datauth.service.UserDataManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("userDataManager")
public class UserDataManagerImpl implements UserDataManager{

    @Autowired(required = false)
	private UserDataDao userDataDao;

    /**
     * setter
     * @param userDataDao
     */
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001020501",caption="列表用户数据")
    @Override
    public List<UserData> getUserDatas(
    	@ConditionCollection(domainClazz=UserData.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return userDataDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020502",caption="主键查询用户数据")
    @Override
    public UserData getUserData(@ServiceParam(name="id") String id) {
    	return userDataDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001020503",caption="分页查询用户数据")
    @Override
	public PagerRecords getPagerUserDatas(Pager pager,//分页条件
			@ConditionCollection(domainClazz=UserData.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = userDataDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001020504",caption="保存用户数据")
    @Override
    public UserData saveUserData(UserData o){
//    	String userDataId = o.getUserDataId();
//    	boolean isUpdate = StringUtils.isNotEmpty(userDataId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return userDataDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001020505",caption="主键删除用户数据")
    @Override
    public void removeUserData(@ServiceParam(name="id") String id){
    	userDataDao.remove(id);
    }
}
