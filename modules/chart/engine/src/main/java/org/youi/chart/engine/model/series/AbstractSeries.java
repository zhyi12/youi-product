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
package org.youi.chart.engine.model.series;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.youi.chart.engine.ISeries;
import org.youi.chart.engine.model.Encode;
import org.youi.chart.engine.model.Label;
import org.youi.chart.engine.model.Tooltip;
import org.youi.chart.engine.model.mark.MarkArea;
import org.youi.chart.engine.model.mark.MarkLine;
import org.youi.chart.engine.model.mark.MarkPoint;
import org.youi.chart.engine.model.style.AreaStyle;
import org.youi.chart.engine.model.style.Emphasis;
import org.youi.chart.engine.model.style.ItemStyle;
import org.youi.chart.engine.model.style.LineStyle;
import org.youi.framework.core.dataobj.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractSeries<T> implements Domain,ISeries {

    private String type;
    private String id;
    private String name;
    private String coordinateSystem;
    private Integer xAxisIndex;
    private Integer yAxisIndex;
    private Integer polarIndex;
    private String symbol;
    private Integer symbolSize;
    private String symbolRotate;
    private String symbolKeepAspect;
    private Integer[] symbolOffset;
    private Boolean showSymbol;
    private String showAllSymbol;
    private Boolean hoverAnimation;
    private Boolean legendHoverLink;
    private String stack;
    private String cursor;
    private Boolean connectNulls;
    private Boolean clip;
    private Boolean step;
    private Label label;
    private ItemStyle itemStyle;
    private LineStyle lineStyle;
    private AreaStyle areaStyle;
    private Emphasis emphasis;
    private Boolean smooth;
    private String smoothMonotone;
    private String sampling;
    private String[] dimensions;
    private Encode encode;
    private String seriesLayoutBy;
    private Integer datasetIndex;
    private List<T> data;
    private MarkPoint markPoint;
    private MarkLine markLine;
    private MarkArea markArea;
    private Integer zlevel;
    private Integer z;
    private Boolean silent;
    private Boolean animation;
    private Integer animationThreshold;
    private Integer animationDuration;
    private String animationEasing;
    private Integer animationDelay;
    private Integer animationDurationUpdate;
    private String animationEasingUpdate;
    private Integer animationDelayUpdate;
    private Tooltip tooltip;

    public String getType() {
        return type;
    }

    public AbstractSeries setType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public AbstractSeries setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbstractSeries setName(String name) {
        this.name = name;
        return this;
    }

    public String getCoordinateSystem() {
        return coordinateSystem;
    }

    public AbstractSeries setCoordinateSystem(String coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        return this;
    }

    public Integer getxAxisIndex() {
        return xAxisIndex;
    }

    public AbstractSeries setxAxisIndex(Integer xAxisIndex) {
        this.xAxisIndex = xAxisIndex;
        return this;
    }

    public Integer getyAxisIndex() {
        return yAxisIndex;
    }

    public AbstractSeries setyAxisIndex(Integer yAxisIndex) {
        this.yAxisIndex = yAxisIndex;
        return this;
    }

    public Integer getPolarIndex() {
        return polarIndex;
    }

    public AbstractSeries setPolarIndex(Integer polarIndex) {
        this.polarIndex = polarIndex;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public AbstractSeries setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public AbstractSeries setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
        return this;
    }

    public String getSymbolRotate() {
        return symbolRotate;
    }

    public AbstractSeries setSymbolRotate(String symbolRotate) {
        this.symbolRotate = symbolRotate;
        return this;
    }

    public String getSymbolKeepAspect() {
        return symbolKeepAspect;
    }

    public AbstractSeries setSymbolKeepAspect(String symbolKeepAspect) {
        this.symbolKeepAspect = symbolKeepAspect;
        return this;
    }

    public Integer[] getSymbolOffset() {
        return symbolOffset;
    }

    public AbstractSeries setSymbolOffset(Integer[] symbolOffset) {
        this.symbolOffset = symbolOffset;
        return this;
    }

    public Boolean getShowSymbol() {
        return showSymbol;
    }

    public AbstractSeries setShowSymbol(Boolean showSymbol) {
        this.showSymbol = showSymbol;
        return this;
    }

    public String getShowAllSymbol() {
        return showAllSymbol;
    }

    public AbstractSeries setShowAllSymbol(String showAllSymbol) {
        this.showAllSymbol = showAllSymbol;
        return this;
    }

    public Boolean getHoverAnimation() {
        return hoverAnimation;
    }

    public AbstractSeries setHoverAnimation(Boolean hoverAnimation) {
        this.hoverAnimation = hoverAnimation;
        return this;
    }

    public Boolean getLegendHoverLink() {
        return legendHoverLink;
    }

    public AbstractSeries setLegendHoverLink(Boolean legendHoverLink) {
        this.legendHoverLink = legendHoverLink;
        return this;
    }

    public String getStack() {
        return stack;
    }

    public AbstractSeries setStack(String stack) {
        this.stack = stack;
        return this;
    }

    public String getCursor() {
        return cursor;
    }

    public AbstractSeries setCursor(String cursor) {
        this.cursor = cursor;
        return this;
    }

    public Boolean getConnectNulls() {
        return connectNulls;
    }

    public AbstractSeries setConnectNulls(Boolean connectNulls) {
        this.connectNulls = connectNulls;
        return this;
    }

    public Boolean getClip() {
        return clip;
    }

    public AbstractSeries setClip(Boolean clip) {
        this.clip = clip;
        return this;
    }

    public Boolean getStep() {
        return step;
    }

    public AbstractSeries setStep(Boolean step) {
        this.step = step;
        return this;
    }

    public Label getLabel() {
        return label;
    }

    public AbstractSeries setLabel(Label label) {
        this.label = label;
        return this;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public AbstractSeries setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public AbstractSeries setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }

    public AreaStyle getAreaStyle() {
        return areaStyle;
    }

    public AbstractSeries setAreaStyle(AreaStyle areaStyle) {
        this.areaStyle = areaStyle;
        return this;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public AbstractSeries setEmphasis(Emphasis emphasis) {
        this.emphasis = emphasis;
        return this;
    }

    public Boolean getSmooth() {
        return smooth;
    }

    public AbstractSeries setSmooth(Boolean smooth) {
        this.smooth = smooth;
        return this;
    }

    public String getSmoothMonotone() {
        return smoothMonotone;
    }

    public AbstractSeries setSmoothMonotone(String smoothMonotone) {
        this.smoothMonotone = smoothMonotone;
        return this;
    }

    public String getSampling() {
        return sampling;
    }

    public AbstractSeries setSampling(String sampling) {
        this.sampling = sampling;
        return this;
    }

    public String[] getDimensions() {
        return dimensions;
    }

    public AbstractSeries setDimensions(String[] dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Encode getEncode() {
        return encode;
    }

    public AbstractSeries setEncode(Encode encode) {
        this.encode = encode;
        return this;
    }

    public String getSeriesLayoutBy() {
        return seriesLayoutBy;
    }

    public AbstractSeries setSeriesLayoutBy(String seriesLayoutBy) {
        this.seriesLayoutBy = seriesLayoutBy;
        return this;
    }

    public Integer getDatasetIndex() {
        return datasetIndex;
    }

    public AbstractSeries setDatasetIndex(Integer datasetIndex) {
        this.datasetIndex = datasetIndex;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public AbstractSeries setData(List<T> data) {
        this.data = data;
        return this;
    }

    /**
     * 添加序列数据项
     * @param iData
     * @return
     */
    public AbstractSeries addData(T iData){
        if(data==null){
            data = new ArrayList<T>();
        }
        data.add(iData);
        return this;
    }

    public MarkPoint getMarkPoint() {
        return markPoint;
    }

    public AbstractSeries setMarkPoint(MarkPoint markPoint) {
        this.markPoint = markPoint;
        return this;
    }

    public MarkLine getMarkLine() {
        return markLine;
    }

    public AbstractSeries setMarkLine(MarkLine markLine) {
        this.markLine = markLine;
        return this;
    }

    public MarkArea getMarkArea() {
        return markArea;
    }

    public AbstractSeries setMarkArea(MarkArea markArea) {
        this.markArea = markArea;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public AbstractSeries setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public AbstractSeries setZ(Integer z) {
        this.z = z;
        return this;
    }

    public Boolean getSilent() {
        return silent;
    }

    public AbstractSeries setSilent(Boolean silent) {
        this.silent = silent;
        return this;
    }

    public Boolean getAnimation() {
        return animation;
    }

    public AbstractSeries setAnimation(Boolean animation) {
        this.animation = animation;
        return this;
    }

    public Integer getAnimationThreshold() {
        return animationThreshold;
    }

    public AbstractSeries setAnimationThreshold(Integer animationThreshold) {
        this.animationThreshold = animationThreshold;
        return this;
    }

    public Integer getAnimationDuration() {
        return animationDuration;
    }

    public AbstractSeries setAnimationDuration(Integer animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    public String getAnimationEasing() {
        return animationEasing;
    }

    public AbstractSeries setAnimationEasing(String animationEasing) {
        this.animationEasing = animationEasing;
        return this;
    }

    public Integer getAnimationDelay() {
        return animationDelay;
    }

    public AbstractSeries setAnimationDelay(Integer animationDelay) {
        this.animationDelay = animationDelay;
        return this;
    }

    public Integer getAnimationDurationUpdate() {
        return animationDurationUpdate;
    }

    public AbstractSeries setAnimationDurationUpdate(Integer animationDurationUpdate) {
        this.animationDurationUpdate = animationDurationUpdate;
        return this;
    }

    public String getAnimationEasingUpdate() {
        return animationEasingUpdate;
    }

    public AbstractSeries setAnimationEasingUpdate(String animationEasingUpdate) {
        this.animationEasingUpdate = animationEasingUpdate;
        return this;
    }

    public Integer getAnimationDelayUpdate() {
        return animationDelayUpdate;
    }

    public AbstractSeries setAnimationDelayUpdate(Integer animationDelayUpdate) {
        this.animationDelayUpdate = animationDelayUpdate;
        return this;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public AbstractSeries setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
