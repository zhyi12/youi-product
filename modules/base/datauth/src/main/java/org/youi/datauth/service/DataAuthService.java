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

import org.youi.datauth.vo.AgencyDataVO;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 *
 * 数据权限服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface DataAuthService {

    /**
     * 根据授权数据类型获取登录机构的数据
     * @param agencyId 登录机构ID，从登录信息中获取
     * @param authDataType 授权数据类型
     * @return
     */
    @EsbServiceMapping(trancode="9001020001",caption="根据授权数据类型获取登录机构的数据")
    AgencyDataVO getLoginAgencyDataIds(
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "authDataType") String authDataType);

    /**
     *
     * @param username 登录用户名，从登录信息中获取
     * @param authDataType 授权数据类型
     * @return
     */
    @EsbServiceMapping(trancode="9001020002",caption="根据授权数据类型获取登录用户的数据")
    List<String> getLoginUserDataIds(
            @ServiceParam(name = "loginUsername",pubProperty = "username") String username,
            @ServiceParam(name = "authDataType") String authDataType);

    /**
     * 分配机构的授权数据
     * @param agencyId 机构ID
     * @param agencyType 机构类型
     * @param authDataType 授权数据类型
     * @param dataIds 授权的数据ID集合
     */
    @EsbServiceMapping(trancode="9001020003",caption="机构数据授权")
    void authAgencyDataIds(
            @ServiceParam(name = "agencyId") String agencyId,
            @ServiceParam(name = "agencyType") String agencyType,
            @ServiceParam(name = "authDataType") String authDataType,
            @ServiceParam(name = "dataIds") String[] dataIds);

    /**
     * 获取机构数据(管理角色)
     * @param agencyId
     * @param authDataType
     * @return
     */
    @RolesAllowed({"ROLE_ADMIN"})
    @EsbServiceMapping(trancode="9001020004",caption="获取机构数据(管理角色)")
    AgencyDataVO getAgencyData(
            @ServiceParam(name = "agencyId") String agencyId,
            @ServiceParam(name = "authDataType") String authDataType);
}
