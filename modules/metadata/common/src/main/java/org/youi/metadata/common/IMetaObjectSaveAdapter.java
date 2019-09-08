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
package org.youi.metadata.common;

import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.common.model.PropertyItem;

import java.util.Collection;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface IMetaObjectSaveAdapter<T extends IMetaObject> {

    /**
     *
     * @param metaObjectName
     * @return
     */
    boolean supports(String metaObjectName);

    /**
     * 创建新的元数据对象
     * @param metaObject
     * @return
     */
    T createMetaObject(T metaObject);

    /**
     * 更新元数据
     * @param id
     * @param propertyItems
     * @return
     */
    T updateMetaObject(String id, Collection<PropertyItem> propertyItems);

    /**
     *
     * @return
     */
    T buildMetaObject();
}
