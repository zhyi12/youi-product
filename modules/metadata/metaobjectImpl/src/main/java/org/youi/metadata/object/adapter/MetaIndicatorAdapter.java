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
package org.youi.metadata.object.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.metadata.common.IMetaObjectGetAdapter;
import org.youi.metadata.common.IMetaObjectRemoveAdapter;
import org.youi.metadata.common.IMetaObjectSaveAdapter;
import org.youi.metadata.common.IMetaParentFinderAdapter;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.object.entity.MetaIndicator;
import org.youi.metadata.object.mongo.MetaIndicatorDao;


import java.util.Collection;

/**
 * 指标元数据对象操作适配处理类
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaIndicatorAdapter implements IMetaParentFinderAdapter,IMetaObjectSaveAdapter<MetaIndicator>,IMetaObjectGetAdapter,IMetaObjectRemoveAdapter {

    @Autowired(required = false)
    private MetaIndicatorDao metaIndicatorDao;

    @Override
    public boolean supports(String metaObjectName) {
        return MetaObjectConstants.META_OBJECT_NAME_INDICATOR.equals(metaObjectName);
    }

    @Override
    public MetaIndicator createMetaObject(MetaIndicator metaObject) {
        return metaIndicatorDao.save(metaObject);
    }

    @Override
    public MetaIndicator updateMetaObject(String id, Collection<PropertyItem> propertyItems) {
        return null;
    }

    @Override
    public MetaIndicator buildMetaObject() {
        return new MetaIndicator();
    }

    @Override
    public boolean exist(String id) {
        return metaIndicatorDao.equals(id);
    }

    @Override
    public IMetaObject getMetaObject(String id) {
        return metaIndicatorDao.get(id);
    }

    @Override
    public void removeMetaObject(String id) {
        metaIndicatorDao.remove(id);
    }

    @Override
    public String findParentMetaObjectName() {
        return MetaObjectConstants.META_OBJECT_NAME_REPORT;
    }
}
