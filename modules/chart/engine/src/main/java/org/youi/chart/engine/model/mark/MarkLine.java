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
package org.youi.chart.engine.model.mark;

import org.youi.chart.engine.model.style.LineStyle;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class MarkLine extends AbstractMark{

    private static final long serialVersionUID = -764030044537078384L;

    private Integer precision;

    private LineStyle lineStyle;

    public Integer getPrecision() {
        return precision;
    }

    public MarkLine setPrecision(Integer precision) {
        this.precision = precision;
        return this;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public MarkLine setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }
}
