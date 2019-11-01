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
package org.youi.chart.engine.model;

import org.youi.chart.engine.model.style.Emphasis;
import org.youi.chart.engine.model.style.ItemStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 *
 * 地图的区域配置
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Region implements Domain{

    private static final long serialVersionUID = 2504623615437306746L;

    private String name;
    private Boolean selected;
    private ItemStyle itemStyle;
    private Label label;
    private Emphasis emphasis;

    public String getName() {
        return name;
    }

    public Region setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getSelected() {
        return selected;
    }

    public Region setSelected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public Region setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public Region setLabel(Label label) {
        this.label = label;
        return this;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public Region setEmphasis(Emphasis emphasis) {
        this.emphasis = emphasis;
        return this;
    }
}
