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
import org.youi.metadata.common.IMetaObjectGetAdapter;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 获取元数据
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaObjectGetDispatcher implements ApplicationContextAware {

    private List<IMetaObjectGetAdapter> metaObjectAdapters;

    /**
     * @setter
     * @param metaObjectAdapters
     */
    public void setMetaObjectAdapters(List<IMetaObjectGetAdapter> metaObjectAdapters) {
        this.metaObjectAdapters = metaObjectAdapters;
    }

    public boolean exist(String id, String metaObjectName) {
        if(!CollectionUtils.isEmpty(metaObjectAdapters)){
            for(IMetaObjectGetAdapter metaObjectGetAdapter:metaObjectAdapters){
                if(metaObjectGetAdapter.supports(metaObjectName)){
                    return metaObjectGetAdapter.exist(id);
                }
            }
        }
        return false;
    }

    public IMetaObject getMetaObject(String id, String metaObjectName){
        if(!CollectionUtils.isEmpty(metaObjectAdapters)){
            for(IMetaObjectGetAdapter metaObjectGetAdapter:metaObjectAdapters){
                if(metaObjectGetAdapter.supports(metaObjectName)){
                    return metaObjectGetAdapter.getMetaObject(id);
                }
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initMetaObjectAdapters(applicationContext);
    }

    private void initMetaObjectAdapters(ApplicationContext context) {
        if (metaObjectAdapters == null) {
            Map<String, IMetaObjectGetAdapter> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IMetaObjectGetAdapter.class, true, false);
            if (adapterBeans != null) {
                metaObjectAdapters = new ArrayList<>(adapterBeans.values());
                metaObjectAdapters.sort(new OrderComparator());
            }
        }
    }

}
