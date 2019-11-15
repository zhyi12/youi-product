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
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.model.FieldItem;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObject;
import org.youi.metadata.project.entity.MetaObjectNode;
import org.youi.metadata.project.vo.MetaObjectVO;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaObjectNodeManager {


    /**
     * 获取元数据节点
     * @return
     */
    @EsbServiceMapping
    MetaObjectNode getMetaObjectNode(@ServiceParam(name = "id") String id);
    /**
     * 获取关联的元数据
     */
    @EsbServiceMapping
    MetaObjectVO getRefMetaObject(@ServiceParam(name = "id")String id);

    /**
     *  创建元数据节点
     * @param loginAreaId
     * @param loginAgencyId
     * @param parentId
     * @param metaObjectNode
     * @return
     */
    @EsbServiceMapping
    MetaObjectNode createMetaObjectNode(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name="parentId") String parentId,
            MetaObjectNode metaObjectNode);

    /**
     * 获取树节点对应的元数据对象的属性集合
     * @param loginAreaId
     * @param loginAgencyId
     * @param id
     * @param metaObjectName
     * @return
     */
    @EsbServiceMapping
    List<FieldItem> getMetaObjectFieldItems(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName);
    /**
     * 更新元数据属性值
     * @param id
     * @return
     */
    MetaObjectNode updateMetaObject(String id,String text,List<PropertyItem> propertyItems);

    /**
     * 更新元数据版本
     * @param id
     * @param text
     * @param propertyItems
     * @return
     */
    MetaObjectNode addNewVersionMetaObject(String id,String text,List<PropertyItem> propertyItems);

    /**
     * 删除元数据节点
     */
    void removeMetaObjectNode(String id);

    /**
     * 获取制度、方案、报表树
     * @param projectId
     * @param urlPrefix
     * @return
     */
    @EsbServiceMapping
    List<TreeNode> getTopProjectMetaObjectTreeNodes(@ServiceParam(name="projectId")String projectId,
                                                    @ServiceParam(name="urlPrefix")String urlPrefix);

    /**
     * 调查项目下的报表节点的子元数据树
     * @param projectId 项目ID
     * @param reportId 报表元数据对象ID
     * @return
     */
    @EsbServiceMapping
    List<TreeNode> getReportMetaObjectTreeNode(
            @ServiceParam(name="projectId") String projectId,
            @ServiceParam(name="reportId") String reportId);
}
