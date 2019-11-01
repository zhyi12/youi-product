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
package org.youi.chart.engine.model.style;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class LineStyle extends AbstractStyle implements Domain{

    private static final long serialVersionUID = 4558427904555033021L;

    private Integer width;

    /**
     * 线的类型。
     可选：
     'solid'
     'dashed'
     'dotted'
     */
    private String type;

    public Integer getWidth() {
        return width;
    }

    public LineStyle setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public String getType() {
        return type;
    }

    public LineStyle setType(String type) {
        this.type = type;
        return this;
    }
}
