/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.dataquery.engine.model;

import org.youi.framework.core.dataobj.cube.Item;

/**
 * 关联维度计算项
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class RefCalculateItem extends Item {

    private static final long serialVersionUID = -2462503018821388995L;

    private String refDimId;//关联计算维度的ID

    private String refType;//关联计算的类型，占比、排名等  expression=ref[refDimId.refItemId]

    public String getRefDimId() {
        return refDimId;
    }

    public RefCalculateItem setRefDimId(String refDimId) {
        this.refDimId = refDimId;
        return this;
    }

    public String getRefType() {
        return refType;
    }

    public RefCalculateItem setRefType(String refType) {
        this.refType = refType;
        return this;
    }
}
