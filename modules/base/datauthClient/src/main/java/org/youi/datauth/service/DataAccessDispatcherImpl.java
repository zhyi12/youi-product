/*
 * Copyright 2012-2018 the original author or authors.
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
package org.youi.datauth.service;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.datauth.Constant;
import org.youi.datauth.entity.AgencyData;
import org.youi.cache.ICacheManager;
import org.youi.framework.core.dataobj.PubContext;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.esb.DataAccesses;
import org.youi.framework.esb.annotation.DataAccess;
import org.youi.framework.security.IDataAccessDispatcher;
import org.youi.framework.services.utils.ConditionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class DataAccessDispatcherImpl implements IDataAccessDispatcher {

    @Autowired(required = false)
    private DataAuthServiceClient dataAuthServiceClient;

    @Autowired(required = false)
    private ICacheManager cacheManager;

    @Override
    public Collection<Condition> getConditions(DataAccess dataAccess, Object[] objects) {
        List<String> dataIds = new ArrayList<>();
        String dataCacheKey = "";//
        if(ArrayUtils.isNotEmpty(objects) && objects[0] instanceof PubContext){
            PubContext pubContext = (PubContext) objects[0];
            dataCacheKey = pubContext.getUsername();
            if(pubContext.getParams().containsKey("agencyId") && !dataAccess.userData()){
                dataCacheKey = pubContext.getParams().get("agencyId").toString();
            }
        }
        //添加数据权限类型名称到key中
        dataCacheKey = dataAccess.name()+"_"+dataCacheKey;
        //从缓存中获取数据
        DataAccesses dataAccesses = getCachedDataAccess(dataCacheKey);
        if(dataAccesses!=null){
            dataIds = Arrays.asList(dataAccesses.getDataIds());
        }else{
            if(dataAccess.userData()){
                //TODO 用户级别的数据权限
            }else {
                //机构级别的数据权限
                AgencyData agencyData = dataAuthServiceClient.getLoginAgencyDataIds(dataAccess.name());
                if(agencyData!=null && !CollectionUtils.isEmpty(agencyData.getDataIds())) {
                    dataIds = agencyData.getDataIds();
                }
            }
            //缓存数据权限
            cachedDataAccess(dataCacheKey,dataIds);
        }

        Collection<Condition> conditions = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dataIds)){
            conditions.add(ConditionUtils.getCondition(dataAccess.property(),Condition.IN,dataIds.toArray(new String[0])));
        }
        return conditions;
    }

    /**
     * 保存到缓存
     * @param dataCacheKey
     * @param dataIds
     */
    private void cachedDataAccess(String dataCacheKey, List<String> dataIds) {
        if(cacheManager!=null){
            DataAccesses dataAccesses = new DataAccesses();
            dataAccesses.setDataIds(dataIds.toArray(new String[0]));
            cacheManager.getCache(Constant.CACHE_DATA_ACCESS).put(dataCacheKey,dataAccesses);
        }
    }

    /**
     * 获取缓存中的授权数据
     * @param dataCacheKey
     * @return
     */
    private DataAccesses getCachedDataAccess(String dataCacheKey) {
        if(cacheManager!=null){
            Object dataAccesses = cacheManager.getDataFromCache(Constant.CACHE_DATA_ACCESS,dataCacheKey);
            if(dataAccesses instanceof DataAccesses){
                return (DataAccesses)dataAccesses;
            }
        }
        return null;
    }
}
