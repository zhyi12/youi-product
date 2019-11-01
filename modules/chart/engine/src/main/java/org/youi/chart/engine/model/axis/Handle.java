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
package org.youi.chart.engine.model.axis;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Handle implements Domain{

    private static final long serialVersionUID = 8849742916428422780L;

    private Boolean show;
    private String icon;
    private Integer size;
    private Integer margin;
    private String color;
    private Integer throttle;
    private Integer shadowBlur;
    private String shadowColor;
    private Integer shadowOffsetX;
    private Integer shadowOffsetY;

    public Boolean getShow() {
        return show;
    }

    public Handle setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Handle setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public Handle setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getMargin() {
        return margin;
    }

    public Handle setMargin(Integer margin) {
        this.margin = margin;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Handle setColor(String color) {
        this.color = color;
        return this;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public Handle setThrottle(Integer throttle) {
        this.throttle = throttle;
        return this;
    }

    public Integer getShadowBlur() {
        return shadowBlur;
    }

    public Handle setShadowBlur(Integer shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public Handle setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public Handle setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public Handle setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }
}
