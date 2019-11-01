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
package org.youi.chart.engine.model.data;

import org.youi.chart.engine.IChartObject;
import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.LabelLine;
import org.youi.chart.engine.model.Tooltip;
import org.youi.chart.engine.model.style.Emphasis;
import org.youi.chart.engine.model.style.ItemStyle;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ChartData implements IChartObject {

    private static final long serialVersionUID = -1826842476288830874L;

    private String name;
    private Double value;
    private Boolean selected;

    private Label label;
    private LabelLine labelLine;
    private ItemStyle itemStyle;
    private Emphasis emphasis;
    private Tooltip tooltip;

    public String getName() {
        return name;
    }

    public ChartData setName(String name) {
        this.name = name;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public ChartData setValue(Double value) {
        this.value = value;
        return this;
    }

    public Boolean getSelected() {
        return selected;
    }

    public ChartData setSelected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public ChartData setLabel(Label label) {
        this.label = label;
        return this;
    }

    public LabelLine getLabelLine() {
        return labelLine;
    }

    public ChartData setLabelLine(LabelLine labelLine) {
        this.labelLine = labelLine;
        return this;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public ChartData setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public ChartData setEmphasis(Emphasis emphasis) {
        this.emphasis = emphasis;
        return this;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public ChartData setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
