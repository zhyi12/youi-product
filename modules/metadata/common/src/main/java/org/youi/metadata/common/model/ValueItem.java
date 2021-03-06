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
package org.youi.metadata.common.model;

import org.youi.framework.core.dataobj.cube.AbstractItem;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ValueItem extends AbstractItem {

    private static final long serialVersionUID = -1439366657814451047L;

    private Double value;//值项

    public ValueItem(String id,Double value) {
        super.setId(id);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public ValueItem setValue(Double value) {
        this.value = value;
        return this;
    }
}
