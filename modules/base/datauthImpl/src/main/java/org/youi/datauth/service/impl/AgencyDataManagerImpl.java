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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.datauth.entity.AgencyData;
import org.youi.datauth.mongo.AgencyDataDao;
import org.youi.datauth.service.AgencyDataManager;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("agencyDataManager")
public class AgencyDataManagerImpl implements AgencyDataManager{

    @Autowired(required = false)
	private AgencyDataDao agencyDataDao;

    /**
     * setter
     * @param agencyDataDao
     */
    public void setAgencyDataDao(AgencyDataDao agencyDataDao) {
        this.agencyDataDao = agencyDataDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="9001020401",caption="列表机构数据")
    @Override
    public List<AgencyData> getAgencyDatas(
    	@ConditionCollection(domainClazz=AgencyData.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return agencyDataDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020402",caption="主键查询机构数据")
    @Override
    public AgencyData getAgencyData(@ServiceParam(name="id") String id) {
    	return agencyDataDao.get(id);
    }
	
	@EsbServiceMapping(trancode="9001020403",caption="分页查询机构数据")
    @Override
	public PagerRecords getPagerAgencyDatas(Pager pager,//分页条件
			@ConditionCollection(domainClazz=AgencyData.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = agencyDataDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="9001020404",caption="保存机构数据")
    @Override
    public AgencyData saveAgencyData(AgencyData o){
//    	String agencyDataId = o.getAgencyDataId();
//    	boolean isUpdate = StringUtils.isNotEmpty(agencyDataId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return agencyDataDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="9001020405",caption="主键删除机构数据")
    @Override
    public void removeAgencyData(@ServiceParam(name="id") String id){
    	agencyDataDao.remove(id);
    }

    @Override
    public AgencyData getDataByAuthDataTypeAndAgencyId(String authDataType,String agencyId ){
        return agencyDataDao.getDataByAuthDataTypeAndAgencyId(authDataType,agencyId);
    }

    @Override
    public void authAgencyDataIds(String agencyId, String agencyType, String authDataType, String[] dataIds) {
        AgencyData agencyData = agencyDataDao.getDataByAuthDataTypeAndAgencyId(authDataType,agencyId);
        if(agencyData==null){
            agencyData = new AgencyData();
            agencyData.setAgencyId(agencyId);
            agencyData.setAgencyType(agencyType);
            agencyData.setAuthDataType(authDataType);
        }
        if(dataIds==null){
            dataIds = new String[]{};
        }
        agencyData.setDataIds(Arrays.asList(dataIds));
        //保存到数据库
        agencyDataDao.save(agencyData);
    }
}
