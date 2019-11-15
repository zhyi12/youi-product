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
import org.youi.metadata.common.IMetaObjectSaveAdapter;
import org.youi.metadata.common.IMetaObjectGetAdapter;
import org.youi.metadata.common.IMetaObjectRemoveAdapter;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.MetaObjectConstants;
import org.youi.metadata.object.entity.MetaPlan;
import org.youi.metadata.object.mongo.MetaPlanDao;

import java.util.Collection;

/**
 * 调查制度元数据对象操作适配处理类
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaPlanTaskAdapter implements
        IMetaObjectGetAdapter,IMetaObjectSaveAdapter<MetaPlan>,IMetaObjectRemoveAdapter {

    @Autowired(required = false)
    private MetaPlanDao metaPlanDao;

    /**
     * @setter
     * @param metaPlanDao
     */
    public void setMetaPlanDao(MetaPlanDao metaPlanDao) {
        this.metaPlanDao = metaPlanDao;
    }

    @Override
    public boolean supports(String metaObjectName) {
        return MetaObjectConstants.META_OBJECT_NAME_PLAN.equals(metaObjectName);
    }

    @Override
    public MetaPlan createMetaObject(MetaPlan metaPlan) {
        return metaPlanDao.save(metaPlan);
    }

    @Override
    public MetaPlan updateMetaObject(String id, Collection<PropertyItem> propertyItems) {
        MetaPlan metaPlan = metaPlanDao.get(id);

        return metaPlan;
    }

    @Override
    public MetaPlan buildMetaObject() {
        return new MetaPlan();
    }

    @Override
    public boolean exist(String id) {
        return metaPlanDao.exists(id);
    }

    @Override
    public IMetaObject getMetaObject(String id) {
        return metaPlanDao.get(id);
    }

    @Override
    public void removeMetaObject(String id) {
        metaPlanDao.remove(id);
    }
}
