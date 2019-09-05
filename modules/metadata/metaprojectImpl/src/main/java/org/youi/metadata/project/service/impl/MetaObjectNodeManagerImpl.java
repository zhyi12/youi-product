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
package org.youi.metadata.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeNodeConfig;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.object.entity.MetaObject;
import org.youi.metadata.project.entity.MetaObjectNode;
import org.youi.metadata.project.mongo.MetaObjectNodeDao;
import org.youi.metadata.project.service.MetaObjectNodeManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaObjectNodeManager")
public class MetaObjectNodeManagerImpl implements MetaObjectNodeManager {

    @Autowired(required = false)
    private MetaObjectNodeDao metaObjectNodeDao;

    @Autowired
    private MetaProjectTreeBuilder metaProjectTreeBuilder;

    /**
     * 创建元数据对象节点
     * @param parentId 父节点id
     * @param metaObjectName 元数据对象名
     * @param refMetaObjectId 关联的元数据ID
     * @param text 节点文本
     * @return
     */
    @Override
    @EsbServiceMapping
    public MetaObjectNode createMetaObjectNode(
            @ServiceParam(name="parentId") String parentId,
            @ServiceParam(name="metaObjectName") String metaObjectName,
            @ServiceParam(name="refMetaObjectId") String refMetaObjectId,
            @ServiceParam(name="text") String text) {
        MetaObjectNode metaObjectNode = new MetaObjectNode();
        metaObjectNode.setText(text);
        metaObjectNode.setMetaObjectName(metaObjectName);
        metaObjectNode.setRefMetaObjectId(refMetaObjectId);

        if(StringUtils.isNotEmpty(parentId) && metaObjectNodeDao.exists(parentId)){
            MetaObjectNode parentMetaObjectNode = metaObjectNodeDao.get(parentId);

            Record metaObjectParents = new Record();
            metaObjectParents.putAll(new HashMap<>(parentMetaObjectNode.getMetaObjectParents()));
            metaObjectParents.put(parentMetaObjectNode.getMetaObjectName(),parentId);

            metaObjectNode.setMetaObjectParents(metaObjectParents);
        }

        return metaObjectNodeDao.save(metaObjectNode);
    }

    @Override
    public MetaObjectNode updateMetaObject(String id, String text, List<PropertyItem> propertyItems) {
        return null;
    }

    @Override
    public MetaObjectNode addNewVersionMetaObject(String id, String text, List<PropertyItem> propertyItems) {
        return null;
    }

    @Override
    public void removeMetaObjectNode(String id) {

    }

    @Override
    public MetaObject getRefMetaObject(String id) {
        return null;
    }


    /**
     * 制度和方案树
     * @param projectId
     * @param urlPrefix
     * @return
     */
    @EsbServiceMapping
    public List<TreeNode> getTopProjectMetaObjectTreeNodes(@ServiceParam(name="projectId") String projectId,
                                                           @ServiceParam(name="urlPrefix") String urlPrefix){
        Collection<String> metaObjectNames = new ArrayList<>();
        metaObjectNames.add(MetaObjectConstants.META_OBJECT_NAME_PLAN);
        metaObjectNames.add(MetaObjectConstants.META_OBJECT_NAME_TASK);
        metaObjectNames.add(MetaObjectConstants.META_OBJECT_NAME_REPORT);
        List<MetaObjectNode> metaObjectNodes = metaObjectNodeDao.findByProjectIdAndMetaObjectNameIn(projectId,metaObjectNames);

        return metaProjectTreeBuilder.buildTreeNodes(MetaObjectConstants.META_OBJECT_NAME_PLAN,metaObjectNodes);
    }

    /**
     * 生成报表的元数据树
     * @param projectId
     * @param reportId
     * @return
     */
    public List<TreeNode> getReportMetaObjectTreeNode(String projectId,String reportId){
        Collection<String> metaObjectNames = new ArrayList<>();
        //主键
        //填报信息
        //物理表
        //指标
        //分组
        //目录
        //报告期属性

        return null;
    }
}
