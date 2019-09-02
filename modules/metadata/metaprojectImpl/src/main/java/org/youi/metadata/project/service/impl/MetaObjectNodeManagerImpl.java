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

import org.springframework.stereotype.Service;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObject;
import org.youi.metadata.project.entity.MetaObjectNode;
import org.youi.metadata.project.service.MetaObjectNodeManager;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaObjectNodeManager")
public class MetaObjectNodeManagerImpl implements MetaObjectNodeManager {

    @Override
    public MetaObjectNode createMetaObjectNode(String parentId, String parentMetaObjectName, String metaObjectName, String refMetaObjectId, String text) {
        return null;
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
}
