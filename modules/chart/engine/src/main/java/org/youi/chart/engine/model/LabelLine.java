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

import org.youi.chart.engine.model.style.LineStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class LabelLine implements Domain{

    private static final long serialVersionUID = 4781667568292030437L;

    private Boolean show;
    private Integer length;
    private Integer length2;
    private Boolean smooth;
    private LineStyle lineStyle;

    public Boolean getShow() {
        return show;
    }

    public LabelLine setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public LabelLine setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Integer getLength2() {
        return length2;
    }

    public LabelLine setLength2(Integer length2) {
        this.length2 = length2;
        return this;
    }

    public Boolean getSmooth() {
        return smooth;
    }

    public LabelLine setSmooth(Boolean smooth) {
        this.smooth = smooth;
        return this;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public LabelLine setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }
}
