/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *private String   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.chart.engine.model.series;

import org.youi.chart.engine.model.Detail;
import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.Pointer;
import org.youi.chart.engine.model.Title;
import org.youi.chart.engine.model.axis.AxisLine;
import org.youi.chart.engine.model.axis.AxisTick;
import org.youi.chart.engine.model.axis.SplitLine;
import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.util.StringUtils;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class GaugeSeries extends AbstractSeries{

    private GaugeSeries(){
        this.setType(StringUtils.lowerCaseFirstLetter(ChartType.Gauge.name()));
    }

    private String radius;//: '75%',
    private Integer startAngle;
    private Integer endAngle;
    private Boolean clockwise;
    private Integer min;
    private Integer max;
    private Integer splitNumber;
    private AxisLine axisLine;
    private SplitLine splitLine;
    private AxisTick axisTick;
    private Label axisLabel;
    private Pointer pointer;
    private Title title;
    private Detail detail;

    public String getRadius() {
        return radius;
    }

    public GaugeSeries setRadius(String radius) {
        this.radius = radius;
        return this;
    }

    public Integer getStartAngle() {
        return startAngle;
    }

    public GaugeSeries setStartAngle(Integer startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public Integer getEndAngle() {
        return endAngle;
    }

    public GaugeSeries setEndAngle(Integer endAngle) {
        this.endAngle = endAngle;
        return this;
    }

    public Boolean getClockwise() {
        return clockwise;
    }

    public GaugeSeries setClockwise(Boolean clockwise) {
        this.clockwise = clockwise;
        return this;
    }

    public Integer getMin() {
        return min;
    }

    public GaugeSeries setMin(Integer min) {
        this.min = min;
        return this;
    }

    public Integer getMax() {
        return max;
    }

    public GaugeSeries setMax(Integer max) {
        this.max = max;
        return this;
    }

    public Integer getSplitNumber() {
        return splitNumber;
    }

    public GaugeSeries setSplitNumber(Integer splitNumber) {
        this.splitNumber = splitNumber;
        return this;
    }

    public AxisLine getAxisLine() {
        return axisLine;
    }

    public GaugeSeries setAxisLine(AxisLine axisLine) {
        this.axisLine = axisLine;
        return this;
    }

    public SplitLine getSplitLine() {
        return splitLine;
    }

    public GaugeSeries setSplitLine(SplitLine splitLine) {
        this.splitLine = splitLine;
        return this;
    }

    public AxisTick getAxisTick() {
        return axisTick;
    }

    public GaugeSeries setAxisTick(AxisTick axisTick) {
        this.axisTick = axisTick;
        return this;
    }

    public Label getAxisLabel() {
        return axisLabel;
    }

    public GaugeSeries setAxisLabel(Label axisLabel) {
        this.axisLabel = axisLabel;
        return this;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public GaugeSeries setPointer(Pointer pointer) {
        this.pointer = pointer;
        return this;
    }

    public Title getTitle() {
        return title;
    }

    public GaugeSeries setTitle(Title title) {
        this.title = title;
        return this;
    }

    public Detail getDetail() {
        return detail;
    }

    public GaugeSeries setDetail(Detail detail) {
        this.detail = detail;
        return this;
    }
}
