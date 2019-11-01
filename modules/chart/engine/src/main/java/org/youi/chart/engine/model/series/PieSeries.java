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

import org.youi.chart.engine.IComplexDataSeries;
import org.youi.chart.engine.model.LabelLine;
import org.youi.chart.engine.model.data.ChartData;
import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.util.StringUtils;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class PieSeries extends AbstractSeries<ChartData> implements IComplexDataSeries {

    private static final long serialVersionUID = 8968481612277969539L;

    private Integer hoverOffset;
    private String selectedMode;
    private Integer selectedOffset;
    private String clockwise;
    private Integer startAngle;
    private Integer minAngle;
    private Integer minShowLabelAngle;
    private String roseType;
    private String avoidLabelOverlap;
    private String stillShowZeroSum;
    private LabelLine labelLine;
    private String[] center;
    private String[] radius;
    private String animationType;
    private String animationTypeUpdate;

    public PieSeries() {
        this.setType(StringUtils.lowerCaseFirstLetter(ChartType.Pie.name()));
    }

    public Integer getHoverOffset() {
        return hoverOffset;
    }

    public PieSeries setHoverOffset(Integer hoverOffset) {
        this.hoverOffset = hoverOffset;
        return this;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public PieSeries setSelectedMode(String selectedMode) {
        this.selectedMode = selectedMode;
        return this;
    }

    public Integer getSelectedOffset() {
        return selectedOffset;
    }

    public PieSeries setSelectedOffset(Integer selectedOffset) {
        this.selectedOffset = selectedOffset;
        return this;
    }

    public String getClockwise() {
        return clockwise;
    }

    public PieSeries setClockwise(String clockwise) {
        this.clockwise = clockwise;
        return this;
    }

    public Integer getStartAngle() {
        return startAngle;
    }

    public PieSeries setStartAngle(Integer startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public Integer getMinAngle() {
        return minAngle;
    }

    public PieSeries setMinAngle(Integer minAngle) {
        this.minAngle = minAngle;
        return this;
    }

    public Integer getMinShowLabelAngle() {
        return minShowLabelAngle;
    }

    public PieSeries setMinShowLabelAngle(Integer minShowLabelAngle) {
        this.minShowLabelAngle = minShowLabelAngle;
        return this;
    }

    public String getRoseType() {
        return roseType;
    }

    public PieSeries setRoseType(String roseType) {
        this.roseType = roseType;
        return this;
    }

    public String getAvoidLabelOverlap() {
        return avoidLabelOverlap;
    }

    public PieSeries setAvoidLabelOverlap(String avoidLabelOverlap) {
        this.avoidLabelOverlap = avoidLabelOverlap;
        return this;
    }

    public String getStillShowZeroSum() {
        return stillShowZeroSum;
    }

    public PieSeries setStillShowZeroSum(String stillShowZeroSum) {
        this.stillShowZeroSum = stillShowZeroSum;
        return this;
    }

    public LabelLine getLabelLine() {
        return labelLine;
    }

    public PieSeries setLabelLine(LabelLine labelLine) {
        this.labelLine = labelLine;
        return this;
    }

    public String[] getCenter() {
        return center;
    }

    public PieSeries setCenter(String[] center) {
        this.center = center;
        return this;
    }

    public String[] getRadius() {
        return radius;
    }

    public PieSeries setRadius(String[] radius) {
        this.radius = radius;
        return this;
    }

    public String getAnimationType() {
        return animationType;
    }

    public PieSeries setAnimationType(String animationType) {
        this.animationType = animationType;
        return this;
    }

    public String getAnimationTypeUpdate() {
        return animationTypeUpdate;
    }

    public PieSeries setAnimationTypeUpdate(String animationTypeUpdate) {
        this.animationTypeUpdate = animationTypeUpdate;
        return this;
    }
}
