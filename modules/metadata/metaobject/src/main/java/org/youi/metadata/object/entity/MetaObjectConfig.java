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
import org.youi.metadata.common.model.FieldItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_object_config")
public class MetaObjectConfig implements Domain{

    private static final long serialVersionUID = 4847947787135016707L;

    @Id
    private String metaObjectName;//元数据对象

    private List<FieldItem> properties;//元数据对象属性集合配置

    public String getMetaObjectName() {
        return metaObjectName;
    }

    public void setMetaObjectName(String metaObjectName) {
        this.metaObjectName = metaObjectName;
    }

    public List<FieldItem> getProperties() {
        return properties;
    }

    public void setProperties(List<FieldItem> properties) {
        this.properties = properties;
    }

    /**
     * 添加属性
     * @param item
     * @return
     */
    public MetaObjectConfig addProperty(FieldItem item){
        if(item !=null){
            if(this.properties == null){
                this.properties = new ArrayList<>();
            }
            this.properties.add(item);
        }
        return this;
    }

    public MetaObjectConfig metaObjectName(String metaObjectName){
        this.setMetaObjectName(metaObjectName);
        return this;
    }

}
