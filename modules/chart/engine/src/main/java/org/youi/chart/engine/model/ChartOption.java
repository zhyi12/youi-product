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
package org.youi.chart.engine.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.ISeries;
import org.youi.chart.engine.model.axis.*;
import org.youi.chart.engine.model.position.Grid;
import org.youi.chart.engine.model.series.*;
import org.youi.chart.engine.model.style.TextStyle;
import org.youi.framework.core.dataobj.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartOption implements Domain,IOption {

    private Title title;
    private Legend legend;
    private Grid[] grid;
    private AxisX[] xAxis;
    private AxisY[] yAxis;
    private Polar polar;
    private RadiusAxis radiusAxis;
    private AngleAxis angleAxis;
    private RadarSeries radar;
    private DataZoom[] dataZoom;
    private VisualMap[] visualMap;
    private Tooltip tooltip;
    private AxisPointer axisPointer;
    private Toolbox toolbox;
    private Brush brush;
    private GeoSeries geo;
    private ParallelSeries parallel;
    private ParallelAxis parallelAxis;
    private SingleAxis singleAxis;
    private Timeline timeline;
    private Graphic[] graphic;
    private CalendarSeries calendar;
    //    private String dataset;
//    private String aria;
    private List<ISeries> series;
    private String color;
    private String backgroundColor;
    private TextStyle textStyle;
    private String animation;
    private String animationThreshold;
    private String animationDuration;
    private String animationEasing;
    private String animationDelay;
    private String animationDurationUpdate;
    private String animationEasingUpdate;
    private String animationDelayUpdate;
    //    private String blendMode;
//    private String hoverLayerThreshold;
    private Boolean useUTC;

    public Title getTitle() {
        return title;
    }

    public ChartOption setTitle(Title title) {
        this.title = title;
        return this;
    }

    public Legend getLegend() {
        return legend;
    }

    public ChartOption setLegend(Legend legend) {
        this.legend = legend;
        return this;
    }

    public Grid[] getGrid() {
        return grid;
    }

    public ChartOption setGrid(Grid[] grid) {
        this.grid = grid;
        return this;
    }

    public AxisX[] getxAxis() {
        return xAxis;
    }

    public ChartOption setxAxis(AxisX[] xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public AxisY[] getyAxis() {
        return yAxis;
    }

    public ChartOption setyAxis(AxisY[] yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    public Polar getPolar() {
        return polar;
    }

    public ChartOption setPolar(Polar polar) {
        this.polar = polar;
        return this;
    }

    public RadiusAxis getRadiusAxis() {
        return radiusAxis;
    }

    public ChartOption setRadiusAxis(RadiusAxis radiusAxis) {
        this.radiusAxis = radiusAxis;
        return this;
    }

    public AngleAxis getAngleAxis() {
        return angleAxis;
    }

    public ChartOption setAngleAxis(AngleAxis angleAxis) {
        this.angleAxis = angleAxis;
        return this;
    }

    public RadarSeries getRadar() {
        return radar;
    }

    public ChartOption setRadar(RadarSeries radar) {
        this.radar = radar;
        return this;
    }

    public DataZoom[] getDataZoom() {
        return dataZoom;
    }

    public ChartOption setDataZoom(DataZoom[] dataZoom) {
        this.dataZoom = dataZoom;
        return this;
    }

    public VisualMap[] getVisualMap() {
        return visualMap;
    }

    public ChartOption setVisualMap(VisualMap[] visualMap) {
        this.visualMap = visualMap;
        return this;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public ChartOption setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public AxisPointer getAxisPointer() {
        return axisPointer;
    }

    public ChartOption setAxisPointer(AxisPointer axisPointer) {
        this.axisPointer = axisPointer;
        return this;
    }

    public Toolbox getToolbox() {
        return toolbox;
    }

    public ChartOption setToolbox(Toolbox toolbox) {
        this.toolbox = toolbox;
        return this;
    }

    public Brush getBrush() {
        return brush;
    }

    public ChartOption setBrush(Brush brush) {
        this.brush = brush;
        return this;
    }

    public GeoSeries getGeo() {
        return geo;
    }

    public ChartOption setGeo(GeoSeries geo) {
        this.geo = geo;
        return this;
    }

    public ParallelSeries getParallel() {
        return parallel;
    }

    public ChartOption setParallel(ParallelSeries parallel) {
        this.parallel = parallel;
        return this;
    }

    public ParallelAxis getParallelAxis() {
        return parallelAxis;
    }

    public ChartOption setParallelAxis(ParallelAxis parallelAxis) {
        this.parallelAxis = parallelAxis;
        return this;
    }

    public SingleAxis getSingleAxis() {
        return singleAxis;
    }

    public ChartOption setSingleAxis(SingleAxis singleAxis) {
        this.singleAxis = singleAxis;
        return this;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public ChartOption setTimeline(Timeline timeline) {
        this.timeline = timeline;
        return this;
    }

    public Graphic[] getGraphic() {
        return graphic;
    }

    public ChartOption setGraphic(Graphic[] graphic) {
        this.graphic = graphic;
        return this;
    }

    public CalendarSeries getCalendar() {
        return calendar;
    }

    public ChartOption setCalendar(CalendarSeries calendar) {
        this.calendar = calendar;
        return this;
    }

    public    List<ISeries> getSeries() {
        return series;
    }

    public ChartOption setSeries(   List<ISeries> series) {
        this.series = series;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ChartOption setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public ChartOption setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public ChartOption setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
        return this;
    }

    public String getAnimation() {
        return animation;
    }

    public ChartOption setAnimation(String animation) {
        this.animation = animation;
        return this;
    }

    public String getAnimationThreshold() {
        return animationThreshold;
    }

    public ChartOption setAnimationThreshold(String animationThreshold) {
        this.animationThreshold = animationThreshold;
        return this;
    }

    public String getAnimationDuration() {
        return animationDuration;
    }

    public ChartOption setAnimationDuration(String animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    public String getAnimationEasing() {
        return animationEasing;
    }

    public ChartOption setAnimationEasing(String animationEasing) {
        this.animationEasing = animationEasing;
        return this;
    }

    public String getAnimationDelay() {
        return animationDelay;
    }

    public ChartOption setAnimationDelay(String animationDelay) {
        this.animationDelay = animationDelay;
        return this;
    }

    public String getAnimationDurationUpdate() {
        return animationDurationUpdate;
    }

    public ChartOption setAnimationDurationUpdate(String animationDurationUpdate) {
        this.animationDurationUpdate = animationDurationUpdate;
        return this;
    }

    public String getAnimationEasingUpdate() {
        return animationEasingUpdate;
    }

    public ChartOption setAnimationEasingUpdate(String animationEasingUpdate) {
        this.animationEasingUpdate = animationEasingUpdate;
        return this;
    }

    public String getAnimationDelayUpdate() {
        return animationDelayUpdate;
    }

    public ChartOption setAnimationDelayUpdate(String animationDelayUpdate) {
        this.animationDelayUpdate = animationDelayUpdate;
        return this;
    }

    public Boolean getUseUTC() {
        return useUTC;
    }

    public ChartOption setUseUTC(Boolean useUTC) {
        this.useUTC = useUTC;
        return this;
    }

    /**
     *
     * @param seriesItem
     * @return
     */
    public ChartOption addSeries(ISeries seriesItem){
        if(series==null){
            series = new ArrayList<>();
        }
        if(seriesItem!=null){
            series.add(seriesItem);
        }
        return this;
    }
}
