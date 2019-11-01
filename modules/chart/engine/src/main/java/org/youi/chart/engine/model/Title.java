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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.youi.chart.engine.model.style.TextStyle;
import org.youi.framework.core.dataobj.Domain;

/**
 *
 * 标题
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Title implements Domain{

    private static final long serialVersionUID = 1828616719623727815L;

    private String id;//
    private Boolean show = true;//
    private String text;//
    private String link;//
    private String target;//ChartType
    private TextStyle textStyle;//
    private String subtext;//
    private String sublink;//
    private String subtarget;//
    private TextStyle subtextStyle;//

    private String textAlign;//
    private String textVerticalAlign;//
    private Boolean triggerEvent;//
    private Integer padding;//
    private Integer itemGap;//
    private Integer zlevel;//
    private Integer z;//

    @JsonDeserialize
    private String left;//
    private String top;//
    private String right;//
    private String bottom;//
    private String backgroundColor;//
    private String borderColor;
    private Integer borderWidth;
    private Integer borderRadius;
    private Integer shadowBlur;
    private String shadowColor;
    private Integer shadowOffsetX;
    private Integer shadowOffsetY;

    public String getId() {
        return id;
    }

    public Title setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getShow() {
        return show;
    }

    public Title setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getText() {
        return text;
    }

    public Title setText(String text) {
        this.text = text;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Title setLink(String link) {
        this.link = link;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public Title setTarget(String target) {
        this.target = target;
        return this;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public Title setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
        return this;
    }

    public String getSubtext() {
        return subtext;
    }

    public Title setSubtext(String subtext) {
        this.subtext = subtext;
        return this;
    }

    public String getSublink() {
        return sublink;
    }

    public Title setSublink(String sublink) {
        this.sublink = sublink;
        return this;
    }

    public String getSubtarget() {
        return subtarget;
    }

    public Title setSubtarget(String subtarget) {
        this.subtarget = subtarget;
        return this;
    }

    public TextStyle getSubtextStyle() {
        return subtextStyle;
    }

    public Title setSubtextStyle(TextStyle subtextStyle) {
        this.subtextStyle = subtextStyle;
        return this;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public Title setTextAlign(String textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    public String getTextVerticalAlign() {
        return textVerticalAlign;
    }

    public Title setTextVerticalAlign(String textVerticalAlign) {
        this.textVerticalAlign = textVerticalAlign;
        return this;
    }

    public Boolean getTriggerEvent() {
        return triggerEvent;
    }

    public Title setTriggerEvent(Boolean triggerEvent) {
        this.triggerEvent = triggerEvent;
        return this;
    }

    public Integer getPadding() {
        return padding;
    }

    public Title setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    public Integer getItemGap() {
        return itemGap;
    }

    public Title setItemGap(Integer itemGap) {
        this.itemGap = itemGap;
        return this;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public Title setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
        return this;
    }

    public Integer getZ() {
        return z;
    }

    public Title setZ(Integer z) {
        this.z = z;
        return this;
    }

    public String getLeft() {
        return left;
    }

    public Title setLeft(String left) {
        this.left = left;
        return this;
    }

    public String getTop() {
        return top;
    }

    public Title setTop(String top) {
        this.top = top;
        return this;
    }

    public String getRight() {
        return right;
    }

    public Title setRight(String right) {
        this.right = right;
        return this;
    }

    public String getBottom() {
        return bottom;
    }

    public Title setBottom(String bottom) {
        this.bottom = bottom;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Title setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Title setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public Title setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public Integer getBorderRadius() {
        return borderRadius;
    }

    public Title setBorderRadius(Integer borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public Integer getShadowBlur() {
        return shadowBlur;
    }

    public Title setShadowBlur(Integer shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public Title setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public Title setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public Title setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }
}
