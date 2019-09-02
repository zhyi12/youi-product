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
package org.youi.metadata.object.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_object_config")
public class MetaObjectConfig implements Domain{

    @Id
    private String id;

    private String metaObjectName;//元数据对象

    private Map<String,Item> properties;//元数据对象属性集合配置

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetaObjectName() {
        return metaObjectName;
    }

    public void setMetaObjectName(String metaObjectName) {
        this.metaObjectName = metaObjectName;
    }

    public Map<String, Item> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Item> properties) {
        this.properties = properties;
    }

    /**
     * 添加属性
     * @param property
     * @param item
     * @return
     */
    public MetaObjectConfig addProperty(String property,Item item){
        if(StringUtils.isNotEmpty(property) && item !=null){
            if(this.properties == null){
                this.properties = new HashMap<>();
            }
            this.properties.put(property,item);
        }
        return this;
    }
}
