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
package org.youi.spcompany.service;

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

import org.youi.spcompany.entity.SpCompany;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface SpCompanyManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="7001010101",caption="主键查询软件开发商")
    List<SpCompany> getSpCompanys(
        @ConditionCollection(domainClazz=SpCompany.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="7001010102",caption="主键查询软件开发商")
    SpCompany getSpCompany(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="7001010103",caption="分页查询软件开发商")
    PagerRecords getPagerSpCompanys(Pager pager,//分页条件
    @ConditionCollection(domainClazz=SpCompany.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="7001010104",caption="保存软件开发商")
    SpCompany saveSpCompany(SpCompany o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="7001010105",caption="主键删除软件开发商")
    void removeSpCompany(@ServiceParam(name="id") String id);
}
