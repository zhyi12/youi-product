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

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.model.FieldItem;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;
import org.youi.metadata.object.entity.MetaObjectConfig;
import org.youi.metadata.object.service.MetaObjectConfigManager;
import org.youi.metadata.object.service.MetaObjectService;

import java.util.Collection;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaObjectService")
public class MetaObjectServiceImpl implements MetaObjectService{

    @Autowired
    private MetaObjectGetDispatcher metaObjectGetDispatcher;

    @Autowired
    private MetaObjectSaveDispatcher metaObjectCreateDispatcher;

    @Autowired
    private MetaObjectRemoveDispatcher metaObjectRemoveDispatcher;

    @Autowired
    private MetaObjectConfigManager metaObjectConfigManager;

    @EsbServiceMapping
    public List<FieldItem> getMetaObjectFieldItems(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName){

        IMetaObject metaObject = this.getMetaObject(areaId,agencyId,id,metaObjectName);
        //属性
        Record properties = metaObject.getProperties();

        MetaObjectConfig metaObjectConfig = metaObjectConfigManager.getMetaObjectConfig(metaObjectName);

        metaObjectConfig.getProperties().forEach(fieldItem -> {
            if(properties!=null && properties.containsKey(fieldItem.getProperty()) && ArrayUtils.isNotEmpty(fieldItem.getValues())){
                fieldItem.setDefaultValue(StringUtils.arrayToDelimitedString(fieldItem.getValues(),","));
            }
        });
        return metaObjectConfig.getProperties();
    }

    @Override
    @EsbServiceMapping
    public IMetaObject getMetaObject(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName) {

        if(!metaObjectGetDispatcher.exist(id,metaObjectName)){
            //如果不存在，则构建新的元数据对象
            return metaObjectCreateDispatcher.createMetaObject(metaObjectName,areaId,agencyId);
        }
        return metaObjectGetDispatcher.getMetaObject(id,metaObjectName);
    }

    @Override
    @EsbServiceMapping
    public IMetaObject updateMetaObject(
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName,
            @DomainCollection(domainClazz = PropertyItem.class, name = "fieldItems") Collection<PropertyItem> fieldItems) {
        return metaObjectCreateDispatcher.updateMetaObject(metaObjectName,id,fieldItems);
    }

    @Override
    @EsbServiceMapping
    public void removeMetaObject(
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName) {

        metaObjectRemoveDispatcher.removeMetaObject(id,metaObjectName);
    }
}
