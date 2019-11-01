/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.chart.engine.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.youi.chart.engine.model.style.TextStyle;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图例
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Legend implements Domain{

    private static final long serialVersionUID = 637624203976364525L;

    /**
     * 当使用 'scroll' 时，这些使用这些设置进行细节配置：
     legend.scrollDataIndex
     legend.pageButtonItemGap
     legend.pageButtonGap
     legend.pageButtonPosition
     legend.pageFormatter
     legend.pageIcons
     legend.pageIconColor
     legend.pageIconInactiveColor
     legend.pageIconSize
     legend.pageTextStyle
     legend.animation
     legend.animationDurationUpdate
     */
    private String type;//"plain"：普通图例。缺省就是普通图例    "scroll"：可滚动翻页的图例。当图例数量较多时可以使用
    private String id;//
    private Boolean show=true;//
    /*
     * zlevel用于 Canvas 分层，不同zlevel值的图形会放置在不同的 Canvas 中，Canvas 分层是一种常见的优化手段。
     * 我们可以把一些图形变化频繁（例如有动画）的组件设置成一个单独的zlevel。需要注意的是过多的 Canvas 会引起内存开销的增大，
     * 在手机端上需要谨慎使用以防崩溃
     */
    private Integer zlevel;//所有图形的 zlevel 值
    private Integer z;//组件的所有图形的z值。控制图形的前后顺序。z值小的图形会被z值大的图形覆盖,z相比zlevel优先级更低，而且不会创建新的 Canvas
    private String left;//
    private String top;
    private String right;
    private String bottom;
    private String width;
    private String height;
    private String orient;
    private String align;
    private Integer padding;
    private Integer itemGap;
    private Integer itemWidth;
    private Integer itemHeight;
    private Boolean symbolKeepAspect;//如果图标（可能来自系列的 symbol 或用户自定义的 legend.data.icon）是 path:// 的形式，是否在缩放时保持该图形的长宽比。

    /*
        <pre>
            // 使用字符串模板，模板变量为图例名称 {name}
            formatter: 'Legend {name}'
            // 使用回调函数
            formatter: function (name) {
                return 'Legend ' + name;
            }
        </pre>
     */
    private String formatter;//用来格式化图例文本，支持字符串模板和回调函数两种形式。

    /**
     * string, boolean
     * 除此之外也可以设成 'single' 或者 'multiple' 使用单选或者多选模式。
     */
    private Boolean selectedMode;//图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。默认开启图例选择，可以设成 false 关闭。

    private String inactiveColor;
    /*
    示例：
        selected: {
            // 选中'系列1'
            '系列1': true,
            // 不选中'系列2'
            '系列2': false
        }
     */
    private Map<String,Boolean> selected;//图例选中状态表。
    private TextStyle textStyle;//

    private String tooltip;//
    private String icon;//
    private List<String> data;//
    private String backgroundColor;//
    private String borderColor;//
    private Integer borderWidth;//
    private Integer borderRadius;//
    private String shadowBlur;//
    private String shadowColor;//
    private Integer shadowOffsetX;//
    private Integer shadowOffsetY;//
    private Integer scrollDataIndex;//
    private Integer pageButtonItemGap;//
    private String pageButtonGap;//
    private String pageButtonPosition;//
    private String pageFormatter;//
    private String pageIcons;//
    private String pageIconColo;//
    private String pageIconInactiveColor;//
    private Integer pageIconSize;//
    private TextStyle pageTextStyle;//
    private String animation;//
    private Integer animationDurationUpdate;//
    private Boolean selector;//
    private String selectorLabel;//
    private String selectorPosition;//
    private Integer selectorItemGap;//
    private Integer selectorButtonGap;//

    public String getType() {
        return type;
    }

    public Legend setType(String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public Legend setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getShow() {
        return show;
    }

    public Legend setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public Legend setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public Legend setZ(Integer z) {
        this.z = z;
        return this;
    }

    public String getLeft() {
        return left;
    }

    public Legend setLeft(String left) {
        this.left = left;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Legend setTop(String top) {
        this.top = top;
        return this;
    }

    public String getRight() {
        return right;
    }

    public Legend setRight(String right) {
        this.right = right;
        return this;
    }

    public String getBottom() {
        return bottom;
    }

    public Legend setBottom(String bottom) {
        this.bottom = bottom;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public Legend setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public Legend setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getOrient() {
        return orient;
    }

    public Legend setOrient(String orient) {
        this.orient = orient;
        return this;
    }

    public String getAlign() {
        return align;
    }

    public Legend setAlign(String align) {
        this.align = align;
        return this;
    }

    public Integer getPadding() {
        return padding;
    }

    public Legend setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    public Integer getItemGap() {
        return itemGap;
    }

    public Legend setItemGap(Integer itemGap) {
        this.itemGap = itemGap;
        return this;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public Legend setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
        return this;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public Legend setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
        return this;
    }

    public Boolean getSymbolKeepAspect() {
        return symbolKeepAspect;
    }

    public Legend setSymbolKeepAspect(Boolean symbolKeepAspect) {
        this.symbolKeepAspect = symbolKeepAspect;
        return this;
    }

    public String getFormatter() {
        return formatter;
    }

    public Legend setFormatter(String formatter) {
        this.formatter = formatter;
        return this;
    }

    public Boolean getSelectedMode() {
        return selectedMode;
    }

    public Legend setSelectedMode(Boolean selectedMode) {
        this.selectedMode = selectedMode;
        return this;
    }

    public String getInactiveColor() {
        return inactiveColor;
    }

    public Legend setInactiveColor(String inactiveColor) {
        this.inactiveColor = inactiveColor;
        return this;
    }

    public Map<String, Boolean> getSelected() {
        return selected;
    }

    public Legend setSelected(Map<String, Boolean> selected) {
        this.selected = selected;
        return this;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public Legend setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public Legend setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Legend setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public List<String> getData() {
        return data;
    }

    public Legend setData(List<String> data) {
        this.data = data;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Legend setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Legend setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public Legend setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public Integer getBorderRadius() {
        return borderRadius;
    }

    public Legend setBorderRadius(Integer borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public String getShadowBlur() {
        return shadowBlur;
    }

    public Legend setShadowBlur(String shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public Legend setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public Legend setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public Legend setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public Integer getScrollDataIndex() {
        return scrollDataIndex;
    }

    public Legend setScrollDataIndex(Integer scrollDataIndex) {
        this.scrollDataIndex = scrollDataIndex;
        return this;
    }

    public Integer getPageButtonItemGap() {
        return pageButtonItemGap;
    }

    public Legend setPageButtonItemGap(Integer pageButtonItemGap) {
        this.pageButtonItemGap = pageButtonItemGap;
        return this;
    }

    public String getPageButtonGap() {
        return pageButtonGap;
    }

    public Legend setPageButtonGap(String pageButtonGap) {
        this.pageButtonGap = pageButtonGap;
        return this;
    }

    public String getPageButtonPosition() {
        return pageButtonPosition;
    }

    public Legend setPageButtonPosition(String pageButtonPosition) {
        this.pageButtonPosition = pageButtonPosition;
        return this;
    }

    public String getPageFormatter() {
        return pageFormatter;
    }

    public Legend setPageFormatter(String pageFormatter) {
        this.pageFormatter = pageFormatter;
        return this;
    }

    public String getPageIcons() {
        return pageIcons;
    }

    public Legend setPageIcons(String pageIcons) {
        this.pageIcons = pageIcons;
        return this;
    }

    public String getPageIconColo() {
        return pageIconColo;
    }

    public Legend setPageIconColo(String pageIconColo) {
        this.pageIconColo = pageIconColo;
        return this;
    }

    public String getPageIconInactiveColor() {
        return pageIconInactiveColor;
    }

    public Legend setPageIconInactiveColor(String pageIconInactiveColor) {
        this.pageIconInactiveColor = pageIconInactiveColor;
        return this;
    }

    public Integer getPageIconSize() {
        return pageIconSize;
    }

    public Legend setPageIconSize(Integer pageIconSize) {
        this.pageIconSize = pageIconSize;
        return this;
    }

    public TextStyle getPageTextStyle() {
        return pageTextStyle;
    }

    public Legend setPageTextStyle(TextStyle pageTextStyle) {
        this.pageTextStyle = pageTextStyle;
        return this;
    }

    public String getAnimation() {
        return animation;
    }

    public Legend setAnimation(String animation) {
        this.animation = animation;
        return this;
    }

    public Integer getAnimationDurationUpdate() {
        return animationDurationUpdate;
    }

    public Legend setAnimationDurationUpdate(Integer animationDurationUpdate) {
        this.animationDurationUpdate = animationDurationUpdate;
        return this;
    }

    public Boolean getSelector() {
        return selector;
    }

    public Legend setSelector(Boolean selector) {
        this.selector = selector;
        return this;
    }

    public String getSelectorLabel() {
        return selectorLabel;
    }

    public Legend setSelectorLabel(String selectorLabel) {
        this.selectorLabel = selectorLabel;
        return this;
    }

    public String getSelectorPosition() {
        return selectorPosition;
    }

    public Legend setSelectorPosition(String selectorPosition) {
        this.selectorPosition = selectorPosition;
        return this;
    }

    public Integer getSelectorItemGap() {
        return selectorItemGap;
    }

    public Legend setSelectorItemGap(Integer selectorItemGap) {
        this.selectorItemGap = selectorItemGap;
        return this;
    }

    public Integer getSelectorButtonGap() {
        return selectorButtonGap;
    }

    public Legend setSelectorButtonGap(Integer selectorButtonGap) {
        this.selectorButtonGap = selectorButtonGap;
        return this;
    }

    public Legend addData(String iData) {
        if(data==null){
            data = new ArrayList<>();
        }
        if(StringUtils.isNotEmpty(iData)){
            data.add(iData);
        }
        return this;
    }
}
