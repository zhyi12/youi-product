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
package org.youi.metadata.project.entity;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;

/**
 * 元数据项目的元数据节点
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_object_node")
public class MetaObjectNode implements Domain{

    @Id
    private String id;

    private String projectId;//所属项目

    private String metaObjectName;//元数据对象名

    private String refMetaObjectId;//关联的元数据对象id

    private String text;

    private String nodeFolderPath;//文件夹节点中文名称路径

    private Record metaObjectParents;//父节点集合

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaObjectName() {
        return metaObjectName;
    }

    public void setMetaObjectName(String metaObjectName) {
        this.metaObjectName = metaObjectName;
    }

    public String getRefMetaObjectId() {
        return refMetaObjectId;
    }

    public void setRefMetaObjectId(String refMetaObjectId) {
        this.refMetaObjectId = refMetaObjectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNodeFolderPath() {
        return nodeFolderPath;
    }

    public void setNodeFolderPath(String nodeFolderPath) {
        this.nodeFolderPath = nodeFolderPath;
    }

    public Record getMetaObjectParents() {
        return metaObjectParents;
    }

    public void setMetaObjectParents(Record metaObjectParents) {
        this.metaObjectParents = metaObjectParents;
    }

    /**
     *
     * @param parentMetaObjectName
     * @param parentMetaObjectId
     * @return
     */
    public MetaObjectNode addMetaObjectParent(String parentMetaObjectName,String parentMetaObjectId){
        if(metaObjectParents==null){
            metaObjectParents = new Record();
        }

        if(StringUtils.isNotEmpty(parentMetaObjectName) && StringUtils.isNotEmpty(parentMetaObjectId)){
            metaObjectParents.put(parentMetaObjectName,parentMetaObjectId);
        }

        return this;
    }

    public MetaObjectNode nodeFolderPath(String nodeFolderPath){
        this.setNodeFolderPath(nodeFolderPath);
        return this;
    }
}
