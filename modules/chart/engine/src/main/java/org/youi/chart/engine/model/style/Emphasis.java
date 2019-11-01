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

import org.youi.chart.engine.model.Label;
import org.youi.framework.core.dataobj.Domain;

/**
 *
 * 图形的高亮样式
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Emphasis implements Domain{

    private static final long serialVersionUID = -1032784057892638990L;

    private Label label;

    private ItemStyle itemStyle;

    public Label getLabel() {
        return label;
    }

    public Emphasis setLabel(Label label) {
        this.label = label;
        return this;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public Emphasis setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }
}
