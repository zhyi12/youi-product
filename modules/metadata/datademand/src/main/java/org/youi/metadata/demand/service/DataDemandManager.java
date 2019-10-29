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
package org.youi.metadata.demand.service;

import java.util.List;
import java.util.Collection;

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.metadata.demand.entity.DataDemand;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface DataDemandManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="8001070201",caption="主键查询数据需求")
    List<DataDemand> getDataDemands(
        @ConditionCollection(domainClazz=DataDemand.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001070202",caption="主键查询数据需求")
    DataDemand getDataDemand(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="8001070203",caption="分页查询数据需求")
    PagerRecords getPagerDataDemands(Pager pager,//分页条件
    @ConditionCollection(domainClazz=DataDemand.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="8001070204",caption="保存数据需求")
    DataDemand saveDataDemand(DataDemand o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="8001070205",caption="主键删除数据需求")
    void removeDataDemand(@ServiceParam(name="id") String id);

    /**
     *
     * @param realmId
     * @return
     */
    @EsbServiceMapping(trancode="8001070206",caption="获取域下的需求集合")
    List<TreeNode> getRealmDataDemandTree(@ServiceParam(name="realmId") String realmId);
}
