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
package org.youi.chart.engine.model.series;

import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.util.StringUtils;

/**
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class BarSeries extends AbstractSeries {

    private static final long serialVersionUID = -4009602639129346089L;

    private String barWidth;
    private String barMaxWidth;
    private String barMinHeight;
    private String barGap;
    private String barCategoryGap;
    private Boolean large;
    private Integer largeThreshold;
    private Integer progressive;
    private Integer progressiveThreshold;
    private String progressiveChunkMode;

    public BarSeries(){
        this.setType(StringUtils.lowerCaseFirstLetter(ChartType.Bar.name()));
    }


    public String getBarWidth() {
        return barWidth;
    }

    public BarSeries setBarWidth(String barWidth) {
        this.barWidth = barWidth;
        return this;
    }

    public String getBarMaxWidth() {
        return barMaxWidth;
    }

    public BarSeries setBarMaxWidth(String barMaxWidth) {
        this.barMaxWidth = barMaxWidth;
        return this;
    }

    public String getBarMinHeight() {
        return barMinHeight;
    }

    public BarSeries setBarMinHeight(String barMinHeight) {
        this.barMinHeight = barMinHeight;
        return this;
    }

    public String getBarGap() {
        return barGap;
    }

    public BarSeries setBarGap(String barGap) {
        this.barGap = barGap;
        return this;
    }

    public String getBarCategoryGap() {
        return barCategoryGap;
    }

    public BarSeries setBarCategoryGap(String barCategoryGap) {
        this.barCategoryGap = barCategoryGap;
        return this;
    }

    public Boolean getLarge() {
        return large;
    }

    public BarSeries setLarge(Boolean large) {
        this.large = large;
        return this;
    }

    public Integer getLargeThreshold() {
        return largeThreshold;
    }

    public BarSeries setLargeThreshold(Integer largeThreshold) {
        this.largeThreshold = largeThreshold;
        return this;
    }

    public Integer getProgressive() {
        return progressive;
    }

    public BarSeries setProgressive(Integer progressive) {
        this.progressive = progressive;
        return this;
    }

    public Integer getProgressiveThreshold() {
        return progressiveThreshold;
    }

    public BarSeries setProgressiveThreshold(Integer progressiveThreshold) {
        this.progressiveThreshold = progressiveThreshold;
        return this;
    }

    public String getProgressiveChunkMode() {
        return progressiveChunkMode;
    }

    public BarSeries setProgressiveChunkMode(String progressiveChunkMode) {
        this.progressiveChunkMode = progressiveChunkMode;
        return this;
    }
}
