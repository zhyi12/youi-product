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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.object.entity.MetaObjectConfig;
import org.youi.metadata.object.mongo.MetaObjectConfigDao;
import org.youi.metadata.object.service.MetaObjectConfigManager;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaObjectConfigManager")
public class MetaObjectConfigManagerImpl implements MetaObjectConfigManager{

    @Autowired
    private MetaObjectConfigDao metaObjectConfigDao;

    @Override
    @EsbServiceMapping(trancode = "",caption = "主键查询元数据配置")
    public MetaObjectConfig getMetaObjectConfig(@ServiceParam(name = "metaObjectName") String metaObjectName) {
        if(!metaObjectConfigDao.exists(metaObjectName)){//没有则创建
            return metaObjectConfigDao.save(new MetaObjectConfig()
                    .metaObjectName(metaObjectName));
        }
        return metaObjectConfigDao.get(metaObjectName);
    }

    @Override
    @EsbServiceMapping
    public MetaObjectConfig saveMetaObjectConfig(MetaObjectConfig metaObjectConfig) {
        return metaObjectConfigDao.save(metaObjectConfig);
    }

    @Override
    @EsbServiceMapping
    public void removeMetaObjectConfig(@ServiceParam(name = "metaObjectName") String metaObjectName) {
         metaObjectConfigDao.remove(metaObjectName);
    }
}
