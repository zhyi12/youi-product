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
package org.youi.datauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.datauth.entity.AgencyData;
import org.youi.datauth.service.AgencyDataGroupManager;
import org.youi.datauth.service.AgencyDataManager;
import org.youi.datauth.service.DataAuthService;
import org.youi.datauth.vo.AgencyDataVO;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataAuthService")
public class DataAuthServiceImpl implements DataAuthService {

    @Autowired
    private AgencyDataGroupManager agencyDataGroupManager;//机构数据组

    @Autowired
    private AgencyDataManager agencyDataManager;//机构数据

    @Override
    @EsbServiceMapping(trancode="9001020001",caption="根据授权数据类型获取登录机构的数据")
    public AgencyDataVO getLoginAgencyDataIds(
            @ServiceParam(name = "loginAgencyId", pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "authDataType") String authDataType) {
        return getAgencyData(agencyId,authDataType);
    }

    @Override
    @EsbServiceMapping(trancode="9001020002",caption="根据授权数据类型获取登录用户的数据")
    public List<String> getLoginUserDataIds(
            @ServiceParam(name = "loginUsername", pubProperty = "username") String username,
            @ServiceParam(name = "authDataType") String authDataType) {
        return null;
    }

    @Override
    @EsbServiceMapping(trancode="9001020003",caption="机构数据授权")
    public void authAgencyDataIds(
            @ServiceParam(name = "agencyId") String agencyId,
            @ServiceParam(name = "agencyType") String agencyType,
            @ServiceParam(name = "authDataType") String authDataType,
            @ServiceParam(name = "dataIds") String[] dataIds){
        agencyDataManager.authAgencyDataIds(agencyId,agencyType,authDataType,dataIds);
    }

    /**
     *
     * @param agencyId 机构ID
     * @param authDataType 授权数据类型
     * @return
     */
    @EsbServiceMapping(trancode="9001020004",caption="机构数据授权(管理角色)")
    public AgencyDataVO getAgencyData(
            @ServiceParam(name = "agencyId") String agencyId,
            @ServiceParam(name = "authDataType") String authDataType){
        List<String> dataIds = this.getAgencyDataIds(agencyId,authDataType);

        AgencyDataVO agencyData = new AgencyDataVO();
        agencyData.setDataIds(dataIds);
        agencyData.setAgencyId(agencyId);
        agencyData.setAuthDataType(authDataType);
        return agencyData;
    }

    /**
     *
     * @param agencyId
     * @param authDataType
     * @return
     */
    private List<String> getAgencyDataIds(
             String agencyId,String authDataType){
        //TODO 启用缓存
        //机构配置的数据
        AgencyData agencyData = agencyDataManager.getDataByAuthDataTypeAndAgencyId(authDataType,agencyId);
        //从机构关联的数据权限组获取的数据ID集合
        List<String> groupDataIds = agencyDataGroupManager.getDataIdsByAuthDataTypeAndAgencyId(authDataType,agencyId);

        Set<String> agencyDataIds = new HashSet<>();
        //合并当前机构配置的数据ID集合
        if(agencyData!=null && !CollectionUtils.isEmpty(agencyData.getDataIds())){
            agencyDataIds.addAll(agencyData.getDataIds());
        }
        //合并机构配置的数据权限组配置的数据ID集合
        if(!CollectionUtils.isEmpty(groupDataIds)){
            agencyDataIds.addAll(groupDataIds);
        }
        return new ArrayList<>(agencyDataIds);
    }
}
