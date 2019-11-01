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
package org.youi.chart.engine;

import org.hibernate.validator.constraints.Length;
import org.youi.chart.engine.model.*;
import org.youi.chart.engine.model.axis.*;
import org.youi.chart.engine.model.position.Grid;
import org.youi.chart.engine.model.series.*;
import org.youi.chart.engine.model.style.TextStyle;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface IOption {

    Title getTitle();

    ChartOption setTitle(Title title);

    Legend getLegend();

    ChartOption setLegend(Legend legend);

    Grid[] getGrid();

    ChartOption setGrid(Grid[] grid);

    AxisX[] getxAxis();

    ChartOption setxAxis(AxisX[] xAxis);

    AxisY[] getyAxis();

    ChartOption setyAxis(AxisY[] yAxis);

    Polar getPolar();

    ChartOption setPolar(Polar polar);

    RadiusAxis getRadiusAxis();

    ChartOption setRadiusAxis(RadiusAxis radiusAxis);

    AngleAxis getAngleAxis();

    ChartOption setAngleAxis(AngleAxis angleAxis);

    RadarSeries getRadar();

    ChartOption setRadar(RadarSeries radar);

    DataZoom[] getDataZoom();

    ChartOption setDataZoom(DataZoom[] dataZoom);

    VisualMap[] getVisualMap();

    ChartOption setVisualMap(VisualMap[] visualMap);

    Tooltip getTooltip();

    ChartOption setTooltip(Tooltip tooltip);

    AxisPointer getAxisPointer();

    ChartOption setAxisPointer(AxisPointer axisPointer);

    Toolbox getToolbox();

    ChartOption setToolbox(Toolbox toolbox);

    Brush getBrush();

    ChartOption setBrush(Brush brush);

    GeoSeries getGeo();

    ChartOption setGeo(GeoSeries geo);

    ParallelSeries getParallel();

    ChartOption setParallel(ParallelSeries parallel);

    ParallelAxis getParallelAxis();

    ChartOption setParallelAxis(ParallelAxis parallelAxis);

    SingleAxis getSingleAxis();

    ChartOption setSingleAxis(SingleAxis singleAxis);

    Timeline getTimeline();

    ChartOption setTimeline(Timeline timeline);

    Graphic[] getGraphic();

    ChartOption setGraphic(Graphic[] graphic);

    CalendarSeries getCalendar();

    ChartOption setCalendar(CalendarSeries calendar);

    List<ISeries> getSeries();

    ChartOption setSeries(List<ISeries> series);

    String getColor();

    ChartOption setColor(String color);

    String getBackgroundColor();

    ChartOption setBackgroundColor(String backgroundColor);

    TextStyle getTextStyle();

    ChartOption setTextStyle(TextStyle textStyle);

    String getAnimation();

    ChartOption setAnimation(String animation);

    String getAnimationThreshold();

    ChartOption setAnimationThreshold(String animationThreshold);

    String getAnimationDuration();

    ChartOption setAnimationDuration(String animationDuration);

    String getAnimationEasing();

    ChartOption setAnimationEasing(String animationEasing);

    String getAnimationDelay();

    ChartOption setAnimationDelay(String animationDelay);

    String getAnimationDurationUpdate();

    ChartOption setAnimationDurationUpdate(String animationDurationUpdate);

    String getAnimationEasingUpdate();

    ChartOption setAnimationEasingUpdate(String animationEasingUpdate);

    String getAnimationDelayUpdate();

    ChartOption setAnimationDelayUpdate(String animationDelayUpdate);

    Boolean getUseUTC();

    ChartOption setUseUTC(Boolean useUTC);

    ChartOption addSeries(ISeries lineSeries);
}
