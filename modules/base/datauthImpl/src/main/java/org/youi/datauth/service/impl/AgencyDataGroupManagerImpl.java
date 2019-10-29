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

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.datauth.entity.DataGroup;
import org.youi.datauth.mongo.DataGroupDao;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.datauth.entity.AgencyDataGroup;
import org.youi.datauth.mongo.AgencyDataGroupDao;
import org.youi.datauth.service.AgencyDataGroupManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("agencyDataGroupManager")
public class AgencyDataGroupManagerImpl implements AgencyDataGroupManager{

    @Autowired(required = false)
	private AgencyDataGroupDao agencyDataGroupDao;

    @Autowired(required = false)
    private DataGroupDao dataGroupDao;

    /**
     * setter
     * @param agencyDataGroupDao
     */
    public void setAgencyDataGroupDao(AgencyDataGroupDao agencyDataGroupDao) {
        this.agencyDataGroupDao = agencyDataGroupDao;
    }

    public void setDataGroupDao(DataGroupDao dataGroupDao) {
        this.dataGroupDao = dataGroupDao;
    }

    /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001020201",caption="列表机构数据权限组")
    @Override
    public List<AgencyDataGroup> getAgencyDataGroups(
    	@ConditionCollection(domainClazz=AgencyDataGroup.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return agencyDataGroupDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020202",caption="主键查询机构数据权限组")
    @Override
    public AgencyDataGroup getAgencyDataGroup(@ServiceParam(name="id") String id) {
    	return agencyDataGroupDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001020203",caption="分页查询机构数据权限组")
    @Override
	public PagerRecords getPagerAgencyDataGroups(Pager pager,//分页条件
			@ConditionCollection(domainClazz=AgencyDataGroup.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = agencyDataGroupDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001020204",caption="保存机构数据权限组")
    @Override
    public AgencyDataGroup saveAgencyDataGroup(AgencyDataGroup o){
//    	String agencyDataGroupId = o.getAgencyDataGroupId();
//    	boolean isUpdate = StringUtils.isNotEmpty(agencyDataGroupId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return agencyDataGroupDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001020205",caption="主键删除机构数据权限组")
    @Override
    public void removeAgencyDataGroup(@ServiceParam(name="id") String id){
    	agencyDataGroupDao.remove(id);
    }

    /**
     *
     * @param authDataType
     * @param agencyId
     * @return
     */
    public List<String> getDataIdsByAuthDataTypeAndAgencyId(String authDataType, String agencyId){
        AgencyDataGroup agencyDataGroup = agencyDataGroupDao.findByAuthDataTypeAndAgencyId(authDataType,agencyId);
        Set<String> dataIds = new HashSet<>();
        if(agencyDataGroup!=null){
            List<String> groupIds = agencyDataGroup.getGroupIds();
            //查找全部的数据组
            List<DataGroup> dataGroups = dataGroupDao.findByIdIn(groupIds);
            if(!CollectionUtils.isEmpty(dataGroups)){
                //遍历数据组，合并数据ID集合
                dataGroups.forEach(dataGroup -> {
                    if(!CollectionUtils.isEmpty(dataGroup.getDataIds())){
                        dataIds.addAll(dataGroup.getDataIds());
                    }
                });
            }
        }
        return new ArrayList<>(dataIds);
    }
}
