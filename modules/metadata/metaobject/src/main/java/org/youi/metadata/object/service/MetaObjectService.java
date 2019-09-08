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
package org.youi.metadata.object.service;

import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.model.FieldItem;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;

import java.util.Collection;
import java.util.List;

/**
 * 元数据对象存储服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaObjectService {

    /**
     * 获取元数据属性数据
     * @param id
     * @param metaObjectName
     * @return
     */
    @EsbServiceMapping
    List<FieldItem> getMetaObjectFieldItems(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName);
    /**
     * 获取元数据对象
     * @param id
     * @param metaObjectName
     * @return
     */
    @EsbServiceMapping
    IMetaObject getMetaObject(
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String agencyId,
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName);

    @EsbServiceMapping
    IMetaObject updateMetaObject(
            @ServiceParam(name = "id") String id,
            @ServiceParam(name = "metaObjectName") String metaObjectName,
            @DomainCollection(domainClazz = PropertyItem.class,name = "fieldItems")  Collection<PropertyItem> fieldItems
    );

    @EsbServiceMapping
    void removeMetaObject(@ServiceParam(name = "id") String id,@ServiceParam(name = "metaObjectName") String metaObjectName);
}
