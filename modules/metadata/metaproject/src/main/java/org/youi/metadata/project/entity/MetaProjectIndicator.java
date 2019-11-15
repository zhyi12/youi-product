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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.tree.TreeAttribute;

import javax.persistence.Column;

/**
 * 调查项目使用的指标
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_project_indicator")
public class MetaProjectIndicator implements Domain{

    private static final long serialVersionUID = 7378685268530773436L;

    @Id
    private String id;//主键

    private String parentId;//父节点

    @Column
    private String text;//指标名称

    @Column
    private String unitId;//计量单位ID

    @Column
    private String metaProjectId;//项目ID

    private Integer num;//指标序号，用于指标排序

    @Column
    private String metaDataItemName;//关联数据项name

    private Long createTime;//创建时间

    private Long updateTime;//更新时间

    @TreeAttribute(TreeAttribute.TREE_ATTR_ID)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @TreeAttribute(TreeAttribute.TREE_ATTR_PID)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @TreeAttribute(TreeAttribute.TREE_ATTR_TEXT)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMetaDataItemName() {
        return metaDataItemName;
    }

    public MetaProjectIndicator setMetaDataItemName(String metaDataItemName) {
        this.metaDataItemName = metaDataItemName;
        return this;
    }

    public String getMetaProjectId() {
        return metaProjectId;
    }

    public MetaProjectIndicator setMetaProjectId(String metaProjectId) {
        this.metaProjectId = metaProjectId;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public MetaProjectIndicator setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public MetaProjectIndicator setNum(Integer num) {
        this.num = num;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public MetaProjectIndicator setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
