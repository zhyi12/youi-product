/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *private String   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.chart.engine.model.position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.youi.chart.engine.model.Tooltip;
import org.youi.chart.engine.serializer.MultiTypePropertySerialize;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grid implements Domain{

    private static final long serialVersionUID = -1382785658765642969L;

    private String id;
    private Boolean show;
    private Integer zlevel;
    private Integer z;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String left;//: '10%';

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String top;//: 60;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String right;//: '10%';

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String bottom;//: 60;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String width;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String height;

    private Boolean containLabel;
    private String backgroundColor;
    private String borderColor;
    private Integer borderWidth;
    private Integer shadowBlur;
    private String shadowColor;
    private Integer shadowOffsetX;
    private Integer shadowOffsetY;
    private Tooltip tooltip;

    public String getId() {
        return id;
    }

    public Grid setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getShow() {
        return show;
    }

    public Grid setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public Grid setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public Grid setZ(Integer z) {
        this.z = z;
        return this;
    }

    public String getLeft() {
        return left;
    }

    public Grid setLeft(String left) {
        this.left = left;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Grid setTop(String top) {
        this.top = top;
        return this;
    }

    public String getRight() {
        return right;
    }

    public Grid setRight(String right) {
        this.right = right;
        return this;
    }

    public String getBottom() {
        return bottom;
    }

    public Grid setBottom(String bottom) {
        this.bottom = bottom;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public Grid setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public Grid setHeight(String height) {
        this.height = height;
        return this;
    }

    public Boolean getContainLabel() {
        return containLabel;
    }

    public Grid setContainLabel(Boolean containLabel) {
        this.containLabel = containLabel;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Grid setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Grid setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public Grid setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public Integer getShadowBlur() {
        return shadowBlur;
    }

    public Grid setShadowBlur(Integer shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public Grid setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public Grid setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public Grid setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public Grid setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
