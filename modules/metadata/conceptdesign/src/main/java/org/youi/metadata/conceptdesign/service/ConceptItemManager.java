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
package org.youi.metadata.conceptdesign.service;

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

import org.youi.metadata.conceptdesign.entity.ConceptItem;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface ConceptItemManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="8001060101",caption="主键查询模型项")
    List<ConceptItem> getConceptItems(
        @ConditionCollection(domainClazz=ConceptItem.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001060102",caption="主键查询模型项")
    ConceptItem getConceptItem(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="8001060103",caption="分页查询模型项")
    PagerRecords getPagerConceptItems(Pager pager,//分页条件
    @ConditionCollection(domainClazz=ConceptItem.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="8001060104",caption="保存模型项")
    ConceptItem saveConceptItem(ConceptItem o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="8001060105",caption="主键删除模型项")
    void removeConceptItem(@ServiceParam(name="id") String id);

    /**
     * 获取数据资源对应的模型项
     * @param dataResourceId 　数据资源ID
     * @return
     */
    @EsbServiceMapping(trancode="8001060106",caption="获取数据资源对应的模型项")
    List<TreeNode> getConceptItemTree(@ServiceParam(name="dataResourceId") String dataResourceId);


    /**
     * 保存子系统项
     * @param o
     * @return
     */
    @EsbServiceMapping(trancode="8001060107",caption="保存子系统项")
    ConceptItem saveSubSystemItem(ConceptItem o);

    /**
     * 保存模块项
     * @param o
     * @param subSystemId
     * @return
     */
    @EsbServiceMapping(trancode="8001060108",caption="保存模块项")
    ConceptItem saveModuleItem(ConceptItem o, @ServiceParam(name = "subSystemId") String subSystemId);
}
