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

import org.youi.metadata.conceptdesign.entity.ConceptDiagram;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface ConceptDiagramManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="8001060201",caption="主键查询概念模型图")
    List<ConceptDiagram> getConceptDiagrams(
        @ConditionCollection(domainClazz=ConceptDiagram.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001060202",caption="主键查询概念模型图")
    ConceptDiagram getConceptDiagram(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="8001060203",caption="分页查询概念模型图")
    PagerRecords getPagerConceptDiagrams(Pager pager,//分页条件
    @ConditionCollection(domainClazz=ConceptDiagram.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="8001060204",caption="保存概念模型图")
    ConceptDiagram saveConceptDiagram(ConceptDiagram o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="8001060205",caption="主键删除概念模型图")
    void removeConceptDiagram(@ServiceParam(name="id") String id);

    /**
     * 获取模块的概念模型树
     * @param moduleId
     * @return
     */
    @EsbServiceMapping(trancode="8001060206",caption="获取模块的概念模型树")
    List<TreeNode> getConceptDiagramTree(@ServiceParam(name="moduleId") String moduleId);


    /**
     * 获取模块的概念模型树
     * @param moduleId
     * @param content
     * @return
     */
    @EsbServiceMapping(trancode="8001060207",caption="获取模块的概念模型树")
    void saveModuleDiagramContent(@ServiceParam(name="moduleId") String moduleId,
                               @ServiceParam(name="content") String content);

    /**
     * 获取模块的概念模型xml
     * @param moduleId
     * @return
     */
    @EsbServiceMapping(trancode="8001060208",caption="获取模块的概念模型xml")
    String getModuleDiagramContent(@ServiceParam(name="moduleId") String moduleId);
}
