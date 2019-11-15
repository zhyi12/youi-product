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
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeNodeConfig;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.model.FieldItem;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.object.entity.MetaObject;
import org.youi.metadata.object.service.MetaObjectService;
import org.youi.metadata.project.entity.MetaObjectNode;
import org.youi.metadata.project.mongo.MetaObjectNodeDao;
import org.youi.metadata.project.service.MetaObjectNodeManager;
import org.youi.metadata.project.vo.MetaObjectVO;

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
    private MetaObjectService metaObjectService;

    @Autowired
    private MetaProjectTreeBuilder metaProjectTreeBuilder;

    /**
     *
     * @return
     */
    @EsbServiceMapping
    public MetaObjectNode getMetaObjectNode(
            @ServiceParam(name = "id") String id){
        return metaObjectNodeDao.get(id);
    }

    /**
     * 创建元数据对象节点
     * @param parentId 父节点id
     * @return
     */
    @Override
    @EsbServiceMapping
    public MetaObjectNode createMetaObjectNode(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name="parentId") String parentId,
            MetaObjectNode metaObjectNode) {

        if(StringUtils.isNotEmpty(parentId) && metaObjectNodeDao.exists(parentId)){
            MetaObjectNode parentMetaObjectNode = metaObjectNodeDao.get(parentId);

            Record metaObjectParents = new Record();
            if(!CollectionUtils.isEmpty(parentMetaObjectNode.getMetaObjectParents())){
                metaObjectParents.putAll(new HashMap<>(parentMetaObjectNode.getMetaObjectParents()));
            }
            metaObjectParents.put(parentMetaObjectNode.getMetaObjectName(),parentId);
            metaObjectNode.setProjectId(parentMetaObjectNode.getProjectId());
            metaObjectNode.setMetaObjectParents(metaObjectParents);
        }

        //查找并创建元数据对象
        IMetaObject metaObject = metaObjectService.getMetaObject(loginAreaId,loginAgencyId,
                metaObjectNode.getRefMetaObjectId(),metaObjectNode.getMetaObjectName());
        //重新设置关联的元数据
        if(metaObject!=null){
            metaObjectNode.setRefMetaObjectId(metaObject.getId());
        }
        return metaObjectNodeDao.save(metaObjectNode);
    }

    @EsbServiceMapping
    public List<FieldItem> getMetaObjectFieldItems(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName){

        if(!metaObjectNodeDao.exists(id)){
            return new ArrayList<>();
        }
        //主键查询节点
        MetaObjectNode metaObjectNode = metaObjectNodeDao.get(id);
        //查询元数据对象
        IMetaObject metaObject = metaObjectService.getMetaObject(loginAreaId,loginAgencyId,
                metaObjectNode.getRefMetaObjectId(),metaObjectNode.getMetaObjectName());
        //更新关联的元数据数据对象
        if(metaObject!=null && !metaObject.getId().equals(metaObjectNode.getRefMetaObjectId())){
            metaObjectNode.setRefMetaObjectId(metaObject.getId());
            metaObjectNodeDao.save(metaObjectNode);
        }

        //获取元数据对象属性集合
        return metaObjectService.getMetaObjectFieldItems(loginAreaId, loginAgencyId, metaObject.getId(), metaObjectName);
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
    @EsbServiceMapping
    public void removeMetaObjectNode(@ServiceParam(name = "id")String id) {
        //删除节点
        metaObjectNodeDao.remove(id);
        //TODO 删除未使用的元数据
    }


    @Override
    @EsbServiceMapping
    public MetaObjectVO getRefMetaObject(@ServiceParam(name = "id")String id) {
        if(metaObjectNodeDao.exists(id)){
            MetaObjectNode metaObjectNode = metaObjectNodeDao.get(id);
            IMetaObject metaObject = metaObjectService.getMetaObject("","",
                    metaObjectNode.getRefMetaObjectId(),metaObjectNode.getMetaObjectName());
            //返回VO对象
            return new MetaObjectVO(metaObject,id);
        }
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

        return metaProjectTreeBuilder.buildTreeNodes(MetaObjectConstants.META_OBJECT_NAME_PLAN,metaObjectNodes,urlPrefix);
    }

    /**
     * 生成报表的元数据树
     * @param projectId
     * @param reportId
     * @return
     */
    @EsbServiceMapping
    public List<TreeNode> getReportMetaObjectTreeNode(
            @ServiceParam(name="projectId") String projectId,
            @ServiceParam(name="reportId") String reportId){
        List<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(new HtmlTreeNode("KEY_"+reportId,"数据主键"));
        //主键
        //填报信息
        treeNodes.add(new HtmlTreeNode("INFO_"+reportId,"填报信息"));
        //物理表
        //指标
        treeNodes.add(new HtmlTreeNode("MAIN_INDICATOR"+reportId,"主要指标表"));
        //分组
        //目录
        //报告期属性

        return treeNodes;
    }
}
