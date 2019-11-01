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
public abstract class AbstractStyle implements Domain{

    private String color;//
    private Integer shadowBlur;
    private String  shadowColor;
    private Integer shadowOffsetX;
    private Integer shadowOffsetY;
    private Double opacity;

    public String getColor() {
        return color;
    }

    public AbstractStyle setColor(String color) {
        this.color = color;
        return this;
    }

    public Integer getShadowBlur() {
        return shadowBlur;
    }

    public AbstractStyle setShadowBlur(Integer shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public AbstractStyle setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public AbstractStyle setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public AbstractStyle setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public Double getOpacity() {
        return opacity;
    }

    public AbstractStyle setOpacity(Double opacity) {
        this.opacity = opacity;
        return this;
    }
}
