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
package org.youi.datauth.service;

import java.util.List;
import java.util.Collection;

import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.datauth.entity.AgencyData;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface AgencyDataManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="9001020401",caption="主键查询机构数据")
    List<AgencyData> getAgencyDatas(
        @ConditionCollection(domainClazz=AgencyData.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="9001020402",caption="主键查询机构数据")
    AgencyData getAgencyData(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="9001020403",caption="分页查询机构数据")
    PagerRecords getPagerAgencyDatas(Pager pager,//分页条件
    @ConditionCollection(domainClazz=AgencyData.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="9001020404",caption="保存机构数据")
    AgencyData saveAgencyData(AgencyData o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="9001020405",caption="主键删除机构数据")
    void removeAgencyData(@ServiceParam(name="id") String id);


    /**
     *
     * @param authDataType
     * @param agencyId
     * @return
     */
    AgencyData getDataByAuthDataTypeAndAgencyId(String authDataType,String agencyId);

    /**
     * 分配机构的授权数据
     * @param agencyId 机构ID
     * @param agencyType 机构类型
     * @param authDataType 授权数据类型
     * @param dataIds 授权的数据ID集合
     */
    void authAgencyDataIds(String agencyId, String agencyType, String authDataType, String[] dataIds);
}
