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
package org.youi.chart.engine.model.axis;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.style.TextStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractAxis implements Domain{

    private String id;
    private Boolean show;
    private Integer gridIndex;
    private String position;
    private Boolean offset;

    /*
    坐标轴类型。可选：
    'value' 数值轴，适用于连续数据。
    'category' 类目轴，适用于离散的类目数据，为该类型时必须通过 data 设置类目数据。
    'time' 时间轴，适用于连续的时序数据，与数值轴相比时间轴带有时间的格式化，在刻度计算上也有所不同，例如会根据跨度的范围来决定使用月，星期，日还是小时范围的刻度。
    'log' 对数轴。适用于对数数据。
     */
    private String type;
    private String name;
    private String nameLocation;
    private TextStyle nameTextStyle;
    private Integer nameGap;
    private Integer nameRotate;
    private Boolean inverse;
    private String[] boundaryGap;//boundaryGap: ['20%', '20%']
    private String min;//number, string, function
    private String max;//number, string, function
    private Boolean scale;
    private Integer splitNumber;
    private Integer minInterval;
    private Integer maxInterval;
    private Integer interval;
    private Integer logBase;
    private Boolean silent;
    private Boolean triggerEvent;

    private AxisLine axisLine;
    private AxisTick axisTick;
    private Label axisLabel;
    private SplitLine splitLine;
    private SplitArea splitArea;
    private String[] data;
    private AxisPointer axisPointer;
    private Integer zlevel;
    private Integer z;


    public String getId() {
        return id;
    }

    public AbstractAxis setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getShow() {
        return show;
    }

    public AbstractAxis setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public Integer getGridIndex() {
        return gridIndex;
    }

    public AbstractAxis setGridIndex(Integer gridIndex) {
        this.gridIndex = gridIndex;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public AbstractAxis setPosition(String position) {
        this.position = position;
        return this;
    }

    public String[] getData() {
        return data;
    }

    public AbstractAxis setData(String[] data) {
        this.data = data;
        return this;
    }

    public Boolean getOffset() {
        return offset;
    }

    public AbstractAxis setOffset(Boolean offset) {
        this.offset = offset;
        return this;
    }

    public String getType() {
        return type;
    }

    public AbstractAxis setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbstractAxis setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public AbstractAxis setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
        return this;
    }

    public TextStyle getNameTextStyle() {
        return nameTextStyle;
    }

    public AbstractAxis setNameTextStyle(TextStyle nameTextStyle) {
        this.nameTextStyle = nameTextStyle;
        return this;
    }

    public Integer getNameGap() {
        return nameGap;
    }

    public AbstractAxis setNameGap(Integer nameGap) {
        this.nameGap = nameGap;
        return this;
    }

    public Integer getNameRotate() {
        return nameRotate;
    }

    public AbstractAxis setNameRotate(Integer nameRotate) {
        this.nameRotate = nameRotate;
        return this;
    }

    public Boolean getInverse() {
        return inverse;
    }

    public AbstractAxis setInverse(Boolean inverse) {
        this.inverse = inverse;
        return this;
    }

    public String[] getBoundaryGap() {
        return boundaryGap;
    }

    public AbstractAxis setBoundaryGap(String[] boundaryGap) {
        this.boundaryGap = boundaryGap;
        return this;
    }

    public String getMin() {
        return min;
    }

    public AbstractAxis setMin(String min) {
        this.min = min;
        return this;
    }

    public String getMax() {
        return max;
    }

    public AbstractAxis setMax(String max) {
        this.max = max;
        return this;
    }

    public Boolean getScale() {
        return scale;
    }

    public AbstractAxis setScale(Boolean scale) {
        this.scale = scale;
        return this;
    }

    public Integer getSplitNumber() {
        return splitNumber;
    }

    public AbstractAxis setSplitNumber(Integer splitNumber) {
        this.splitNumber = splitNumber;
        return this;
    }

    public Integer getMinInterval() {
        return minInterval;
    }

    public AbstractAxis setMinInterval(Integer minInterval) {
        this.minInterval = minInterval;
        return this;
    }

    public Integer getMaxInterval() {
        return maxInterval;
    }

    public AbstractAxis setMaxInterval(Integer maxInterval) {
        this.maxInterval = maxInterval;
        return this;
    }

    public Integer getInterval() {
        return interval;
    }

    public AbstractAxis setInterval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public Integer getLogBase() {
        return logBase;
    }

    public AbstractAxis setLogBase(Integer logBase) {
        this.logBase = logBase;
        return this;
    }

    public Boolean getSilent() {
        return silent;
    }

    public AbstractAxis setSilent(Boolean silent) {
        this.silent = silent;
        return this;
    }

    public Boolean getTriggerEvent() {
        return triggerEvent;
    }

    public AbstractAxis setTriggerEvent(Boolean triggerEvent) {
        this.triggerEvent = triggerEvent;
        return this;
    }

    public AxisLine getAxisLine() {
        return axisLine;
    }

    public AbstractAxis setAxisLine(AxisLine axisLine) {
        this.axisLine = axisLine;
        return this;
    }

    public AxisTick getAxisTick() {
        return axisTick;
    }

    public AbstractAxis setAxisTick(AxisTick axisTick) {
        this.axisTick = axisTick;
        return this;
    }

    public Label getAxisLabel() {
        return axisLabel;
    }

    public AbstractAxis setAxisLabel(Label axisLabel) {
        this.axisLabel = axisLabel;
        return this;
    }

    public SplitLine getSplitLine() {
        return splitLine;
    }

    public AbstractAxis setSplitLine(SplitLine splitLine) {
        this.splitLine = splitLine;
        return this;
    }

    public SplitArea getSplitArea() {
        return splitArea;
    }

    public AbstractAxis setSplitArea(SplitArea splitArea) {
        this.splitArea = splitArea;
        return this;
    }

    public AxisPointer getAxisPointer() {
        return axisPointer;
    }

    public AbstractAxis setAxisPointer(AxisPointer axisPointer) {
        this.axisPointer = axisPointer;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public AbstractAxis setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public AbstractAxis setZ(Integer z) {
        this.z = z;
        return this;
    }
}
