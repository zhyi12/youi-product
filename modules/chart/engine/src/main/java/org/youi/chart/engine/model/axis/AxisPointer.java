/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.chart.engine.model.axis;

import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.Link;
import org.youi.chart.engine.model.style.LineStyle;
import org.youi.chart.engine.model.style.ShadowStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class AxisPointer implements Domain{

    private static final long serialVersionUID = -2076104118864291692L;

    private String id;
    private Boolean show;
    private String type;//'line' 直线指示器,'shadow' 阴影指示器,'none' 无指示器
    private Boolean snap;
    private Integer z;
    private Label label;
    private LineStyle lineStyle;
    private ShadowStyle shadowStyle;
    private Boolean  triggerTooltip;
    private String value;
    private String status;//当前的状态，可取值为 'show' 和 'hide'。
    private Handle handle;
    private Link[] link;
    private String triggerOn;

    public String getId() {
        return id;
    }

    public AxisPointer setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getShow() {
        return show;
    }

    public AxisPointer setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getType() {
        return type;
    }

    public AxisPointer setType(String type) {
        this.type = type;
        return this;
    }

    public Boolean getSnap() {
        return snap;
    }

    public AxisPointer setSnap(Boolean snap) {
        this.snap = snap;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public AxisPointer setZ(Integer z) {
        this.z = z;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public AxisPointer setLabel(Label label) {
        this.label = label;
        return this;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public AxisPointer setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }

    public ShadowStyle getShadowStyle() {
        return shadowStyle;
    }

    public AxisPointer setShadowStyle(ShadowStyle shadowStyle) {
        this.shadowStyle = shadowStyle;
        return this;
    }

    public Boolean getTriggerTooltip() {
        return triggerTooltip;
    }

    public AxisPointer setTriggerTooltip(Boolean triggerTooltip) {
        this.triggerTooltip = triggerTooltip;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AxisPointer setValue(String value) {
        this.value = value;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AxisPointer setStatus(String status) {
        this.status = status;
        return this;
    }

    public Handle getHandle() {
        return handle;
    }

    public AxisPointer setHandle(Handle handle) {
        this.handle = handle;
        return this;
    }

    public Link[] getLink() {
        return link;
    }

    public AxisPointer setLink(Link[] link) {
        this.link = link;
        return this;
    }

    public String getTriggerOn() {
        return triggerOn;
    }

    public AxisPointer setTriggerOn(String triggerOn) {
        this.triggerOn = triggerOn;
        return this;
    }
}
