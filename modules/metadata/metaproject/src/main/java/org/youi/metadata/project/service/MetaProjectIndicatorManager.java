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
package org.youi.metadata.project.service;

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.project.entity.MetaProjectIndicator;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaProjectIndicatorManager {

    /**
     * 主键查询指标
     * @param indicatorId
     */
    @EsbServiceMapping(trancode = "8001020201",caption = "主键查询指标")
    MetaProjectIndicator getMetaProjectIndicator(@ServiceParam(name = "id") String indicatorId);

    /**
     * 保存指标
     */
    @EsbServiceMapping(trancode = "8001020204",caption = "保存指标")
    MetaProjectIndicator saveMetaProjectIndicator(MetaProjectIndicator metaProjectIndicator);

    /**
     * 主键删除指标
     * 需要检查指标是否已经被项目的报表引用，引用的指标需要通过强制删除交易删除
     */
    @EsbServiceMapping(trancode = "8001020205",caption = "主键删除指标")
    void removeMetaProjectIndicator(@ServiceParam(name = "id") String indicatorId);

    /**
     * 新增指标 - 树形结构的新指标集合
     * @param parentId 指标父节点
     * @param metaProjectIndicators 指标集合
     * @return 返回树结构数据
     */
    @EsbServiceMapping(trancode = "8001020206",caption = "批量新增指标")
    List<TreeNode> addTreeIndicators(
            @ServiceParam(name = "projectId") String projectId,
            @ServiceParam(name = "parentId") String parentId,
            @DomainCollection(name = "indicators",domainClazz = MetaProjectIndicator.class) List<MetaProjectIndicator> metaProjectIndicators);

    /**
     * 获取项目的指标树
     * 一个项目的指标数量一般情况少于1000，一次性加载项目指标树
     *
     * @param metaProjectId
     * @return
     */
    @EsbServiceMapping(trancode = "8001020207",caption = "获取项目的指标树")
    List<TreeNode> getProjectIndicatorTree(@ServiceParam(name = "metaProjectId")String metaProjectId);


}
