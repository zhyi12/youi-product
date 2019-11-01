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
package org.youi.chart.engine.model.axis;

import org.youi.chart.engine.model.Tooltip;
import org.youi.framework.core.dataobj.Domain;

/**
 * 极坐标系，可以用于散点图和折线图。每个极坐标系拥有一个角度轴和一个半径轴。
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Polar implements Domain{

    private static final long serialVersionUID = -8780962410745783156L;

    private String id;
    private Integer zlevel;
    private Integer  z;
    private String[] center;// : ['50%', '50%'],
    private String[] radius;//number, string, Array
    private Tooltip tooltip;

    public String getId() {
        return id;
    }

    public Polar setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public Polar setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public Polar setZ(Integer z) {
        this.z = z;
        return this;
    }

    public String[] getCenter() {
        return center;
    }

    public Polar setCenter(String[] center) {
        this.center = center;
        return this;
    }

    public String[] getRadius() {
        return radius;
    }

    public Polar setRadius(String[] radius) {
        this.radius = radius;
        return this;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public Polar setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
