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

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class AngleAxis extends AbstractAxis{

    private static final long serialVersionUID = 3405416955576213281L;

    private Integer polarIndex;
    private Integer  startAngle;
    private Boolean clockwise;

    public Integer getPolarIndex() {
        return polarIndex;
    }

    public AngleAxis setPolarIndex(Integer polarIndex) {
        this.polarIndex = polarIndex;
        return this;
    }

    public Integer getStartAngle() {
        return startAngle;
    }

    public AngleAxis setStartAngle(Integer startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public Boolean getClockwise() {
        return clockwise;
    }

    public AngleAxis setClockwise(Boolean clockwise) {
        this.clockwise = clockwise;
        return this;
    }
}
