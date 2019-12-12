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
package org.youi.periodfile.service.impl;

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

import org.youi.periodfile.entity.PeriodFileConfig;
import org.youi.periodfile.mongo.PeriodFileConfigDao;
import org.youi.periodfile.service.PeriodFileConfigManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("periodFileConfigManager")
public class PeriodFileConfigManagerImpl implements PeriodFileConfigManager{

    @Autowired(required = false)
	private PeriodFileConfigDao periodFileConfigDao;

    /**
     * setter
     * @param periodFileConfigDao
     */
    public void setPeriodFileConfigDao(PeriodFileConfigDao periodFileConfigDao) {
        this.periodFileConfigDao = periodFileConfigDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8005080201",caption="列表固定期文件报表配置")
    @Override
    public List<PeriodFileConfig> getPeriodFileConfigs(
    	@ConditionCollection(domainClazz=PeriodFileConfig.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return periodFileConfigDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8005080202",caption="主键查询固定期文件报表配置")
    @Override
    public PeriodFileConfig getPeriodFileConfig(@ServiceParam(name="periodFileId") String id) {
        if(!periodFileConfigDao.exists(id)){
            return new PeriodFileConfig();
        }
    	return periodFileConfigDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8005080203",caption="分页查询固定期文件报表配置")
    @Override
	public PagerRecords getPagerPeriodFileConfigs(Pager pager,//分页条件
			@ConditionCollection(domainClazz=PeriodFileConfig.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = periodFileConfigDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8005080204",caption="保存固定期文件报表配置")
    @Override
    public PeriodFileConfig savePeriodFileConfig(PeriodFileConfig o){
//    	String periodFileConfigId = o.getPeriodFileConfigId();
//    	boolean isUpdate = StringUtils.isNotEmpty(periodFileConfigId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return periodFileConfigDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8005080205",caption="主键删除固定期文件报表配置")
    @Override
    public void removePeriodFileConfig(@ServiceParam(name="periodFileId") String id){
    	periodFileConfigDao.remove(id);
    }
}
