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
package org.youi.metadata.project.vo;

import org.youi.framework.util.BeanUtils;
import org.youi.metadata.common.model.IMetaObject;
import org.youi.metadata.object.entity.MetaObject;

/**
 *
 * 元数据 VO 对象
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class MetaObjectVO extends MetaObject{

    private static final long serialVersionUID = -8405439963352991747L;

    private String metaObjectNodeId;//当前树节点ID

    private Long begDate;//开始时间

    private Long endDate;//结束时间

    public MetaObjectVO(IMetaObject metaObject,String metaObjectNodeId) {
        //拷贝属性
        BeanUtils.copyProperties(metaObject,this);
        this.metaObjectNodeId = metaObjectNodeId;
    }

    public String getMetaObjectNodeId() {
        return metaObjectNodeId;
    }

    public void setMetaObjectNodeId(String metaObjectNodeId) {
        this.metaObjectNodeId = metaObjectNodeId;
    }

    public Long getBegDate() {
        return begDate;
    }

    public void setBegDate(Long begDate) {
        this.begDate = begDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
