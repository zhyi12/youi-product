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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.youi.chart.engine.model.style.TextStyle;
import org.youi.chart.engine.serializer.MultiTypePropertySerialize;
import org.youi.framework.core.dataobj.Domain;

/**
 * 提示条
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tooltip implements Domain {

    private static final long serialVersionUID = -7049430447988206863L;

    private Boolean show;
    private String trigger;
    private String axisPointer;

    private Boolean showContent;
    private Boolean alwaysShowContent;
    private String triggerOn;
    private Integer showDelay;
    private Integer hideDelay;
    private Boolean enterable;
    private String renderMode;
    private Boolean confine;
    private Double transitionDuration;
    private String position;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String formatter;

    private String backgroundColor;
    private String borderColor;
    private Integer borderWidth;
    private Integer padding;
    private TextStyle textStyle;
    private String extraCssText;

    public Boolean getShow() {
        return show;
    }

    public Tooltip setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getTrigger() {
        return trigger;
    }

    public Tooltip setTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public String getAxisPointer() {
        return axisPointer;
    }

    public Tooltip setAxisPointer(String axisPointer) {
        this.axisPointer = axisPointer;
        return this;
    }

    public Boolean getShowContent() {
        return showContent;
    }

    public Tooltip setShowContent(Boolean showContent) {
        this.showContent = showContent;
        return this;
    }

    public Boolean getAlwaysShowContent() {
        return alwaysShowContent;
    }

    public Tooltip setAlwaysShowContent(Boolean alwaysShowContent) {
        this.alwaysShowContent = alwaysShowContent;
        return this;
    }

    public String getTriggerOn() {
        return triggerOn;
    }

    public Tooltip setTriggerOn(String triggerOn) {
        this.triggerOn = triggerOn;
        return this;
    }

    public Integer getShowDelay() {
        return showDelay;
    }

    public Tooltip setShowDelay(Integer showDelay) {
        this.showDelay = showDelay;
        return this;
    }

    public Integer getHideDelay() {
        return hideDelay;
    }

    public Tooltip setHideDelay(Integer hideDelay) {
        this.hideDelay = hideDelay;
        return this;
    }

    public Boolean getEnterable() {
        return enterable;
    }

    public Tooltip setEnterable(Boolean enterable) {
        this.enterable = enterable;
        return this;
    }

    public String getRenderMode() {
        return renderMode;
    }

    public Tooltip setRenderMode(String renderMode) {
        this.renderMode = renderMode;
        return this;
    }

    public Boolean getConfine() {
        return confine;
    }

    public Tooltip setConfine(Boolean confine) {
        this.confine = confine;
        return this;
    }

    public Double getTransitionDuration() {
        return transitionDuration;
    }

    public Tooltip setTransitionDuration(Double transitionDuration) {
        this.transitionDuration = transitionDuration;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Tooltip setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getFormatter() {
        return formatter;
    }

    public Tooltip setFormatter(String formatter) {
        this.formatter = formatter;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Tooltip setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Tooltip setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public Tooltip setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public Integer getPadding() {
        return padding;
    }

    public Tooltip setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public Tooltip setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
        return this;
    }

    public String getExtraCssText() {
        return extraCssText;
    }

    public Tooltip setExtraCssText(String extraCssText) {
        this.extraCssText = extraCssText;
        return this;
    }
}
