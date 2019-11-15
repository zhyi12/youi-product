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
import org.youi.metadata.object.entity.MetaReport;
import org.youi.metadata.object.mongo.MetaReportDao;

import java.util.Collection;

/**
 * 报表元数据对象操作适配处理类
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaReportAdapter implements IMetaParentFinderAdapter,IMetaObjectSaveAdapter<MetaReport>,IMetaObjectGetAdapter,IMetaObjectRemoveAdapter {

    @Autowired
    private MetaReportDao metaReportDao;

    @Override
    public boolean supports(String metaObjectName) {
        return MetaObjectConstants.META_OBJECT_NAME_REPORT.equals(metaObjectName);
    }

    @Override
    public MetaReport createMetaObject(MetaReport metaObject) {
        return metaReportDao.save(metaObject);
    }

    @Override
    public MetaReport updateMetaObject(String id, Collection<PropertyItem> propertyItems) {
        return null;
    }

    @Override
    public MetaReport buildMetaObject() {
        return new MetaReport();
    }

    @Override
    public boolean exist(String id) {
        return metaReportDao.exists(id);
    }

    @Override
    public IMetaObject getMetaObject(String id) {
        return metaReportDao.get(id);
    }

    @Override
    public void removeMetaObject(String id) {
        metaReportDao.remove(id);
    }

    public String findParentMetaObjectName(){
        return MetaObjectConstants.META_OBJECT_NAME_TASK;
    }
}
