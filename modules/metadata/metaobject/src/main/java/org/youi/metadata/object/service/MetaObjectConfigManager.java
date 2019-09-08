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

import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.object.entity.MetaObjectConfig;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaObjectConfigManager {

    /**
     * 主键查询元数据配置
     * @param metaObjectName
     * @return
     */
    @EsbServiceMapping
    MetaObjectConfig getMetaObjectConfig(@ServiceParam(name = "metaObjectName") String metaObjectName);

    /**
     * 保存元数据配置
     * @param metaObjectConfig
     * @return
     */
    @EsbServiceMapping
    MetaObjectConfig saveMetaObjectConfig(MetaObjectConfig metaObjectConfig);

    /**
     * 删除元数据配置
     * @param metaObjectName
     * @return
     */
    @EsbServiceMapping
    void removeMetaObjectConfig(@ServiceParam(name = "metaObjectName") String metaObjectName);
}
