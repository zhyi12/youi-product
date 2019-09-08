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
package org.youi.metadata.object.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.metadata.common.IMetaObjectSaveAdapter;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 创建元数据
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaObjectSaveDispatcher implements ApplicationContextAware {

    private List<IMetaObjectSaveAdapter> metaObjectAdapters;

    /**
     * @setter
     * @param metaObjectAdapters
     */
    public void setMetaObjectAdapters(List<IMetaObjectSaveAdapter> metaObjectAdapters) {
        this.metaObjectAdapters = metaObjectAdapters;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initMetaObjectAdapters(applicationContext);
    }

    private void initMetaObjectAdapters(ApplicationContext context) {
        if (metaObjectAdapters == null) {
            Map<String, IMetaObjectSaveAdapter> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IMetaObjectSaveAdapter.class, true, false);
            if (adapterBeans != null) {
                metaObjectAdapters = new ArrayList<>(adapterBeans.values());
                metaObjectAdapters.sort(new OrderComparator());
            }
        }
    }

    /**
     * 创建元数据对象
     * @param metaObjectName
     * @param areaId
     * @param agencyId
     * @return
     */
    public IMetaObject createMetaObject(String metaObjectName, String areaId, String agencyId) {
        if(!CollectionUtils.isEmpty(metaObjectAdapters)){
            for(IMetaObjectSaveAdapter metaObjectCreateAdapter:metaObjectAdapters){
                if(metaObjectCreateAdapter.supports(metaObjectName)){
                    MetaObject metaObject = (MetaObject)metaObjectCreateAdapter.buildMetaObject();
                    return metaObjectCreateAdapter.createMetaObject(metaObject.agencyId(agencyId).areaId(areaId));
                }
            }
        }
        return null;
    }

    /**
     * 更新元数据对象
     * @param metaObjectName
     * @param id
     * @param propertyItems
     * @return
     */
    public IMetaObject updateMetaObject(String metaObjectName,String id, Collection<PropertyItem> propertyItems) {
        if(!CollectionUtils.isEmpty(metaObjectAdapters)){
            for(IMetaObjectSaveAdapter metaObjectCreateAdapter:metaObjectAdapters){
                if(metaObjectCreateAdapter.supports(metaObjectName)){
                    return metaObjectCreateAdapter.updateMetaObject(id,propertyItems);
                }
            }
        }
        return null;
    }
}
