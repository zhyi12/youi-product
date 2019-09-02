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
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObject;
import org.youi.metadata.project.entity.MetaObjectNode;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaObjectNodeManager {


    /**
     * 获取关联的元数据
     */
    MetaObject getRefMetaObject(String id);

    //List<TreeNode> get

    /**
     * 创建元数据节点
     * @param parentId 父节点id
     * @param parentMetaObjectName 父节点元数据类型
     * @param metaObjectName 元数据对象名
     * @param refMetaObjectId 关联的元数据ID
     * @param text 节点文本
     * @return
     */
    MetaObjectNode createMetaObjectNode(
            String parentId,
            String parentMetaObjectName,
            String metaObjectName,
            String refMetaObjectId,
            String text
    );

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

}
