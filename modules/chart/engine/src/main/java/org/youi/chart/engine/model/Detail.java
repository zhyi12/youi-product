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

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Detail implements Domain {

    private static final long serialVersionUID = 8933872758159142530L;

    private Boolean show;
    private String width;
    private String height;
    private String backgroundColor;
    private Integer borderWidth;
    private String borderColor;
    private String[] offsetCenter;
    private String formatter;
    private String color;
    private String fontStyle;
    private String fontWeight;
    private String fontFamily;
    private Integer fontSize;
    private String lineHeight;
    private Integer borderRadius;
    private Integer padding;
    private String shadowColor;
    private Integer shadowBlur;
    private Integer shadowOffsetX;
    private Integer shadowOffsetY;
    private String textBorderColor;
    private Integer textBorderWidth;
    private String textShadowColor;
    private Integer textShadowBlur;
    private Integer textShadowOffsetX;
    private Integer textShadowOffsetY;
//private String rich: {...};


    public Boolean getShow() {
        return show;
    }

    public Detail setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public Detail setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public Detail setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Detail setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public Detail setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Detail setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public String[] getOffsetCenter() {
        return offsetCenter;
    }

    public Detail setOffsetCenter(String[] offsetCenter) {
        this.offsetCenter = offsetCenter;
        return this;
    }

    public String getFormatter() {
        return formatter;
    }

    public Detail setFormatter(String formatter) {
        this.formatter = formatter;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Detail setColor(String color) {
        this.color = color;
        return this;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public Detail setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public Detail setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
        return this;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public Detail setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public Detail setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public String getLineHeight() {
        return lineHeight;
    }

    public Detail setLineHeight(String lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public Integer getBorderRadius() {
        return borderRadius;
    }

    public Detail setBorderRadius(Integer borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public Integer getPadding() {
        return padding;
    }

    public Detail setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public Detail setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public Integer getShadowBlur() {
        return shadowBlur;
    }

    public Detail setShadowBlur(Integer shadowBlur) {
        this.shadowBlur = shadowBlur;
        return this;
    }

    public Integer getShadowOffsetX() {
        return shadowOffsetX;
    }

    public Detail setShadowOffsetX(Integer shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public Integer getShadowOffsetY() {
        return shadowOffsetY;
    }

    public Detail setShadowOffsetY(Integer shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public String getTextBorderColor() {
        return textBorderColor;
    }

    public Detail setTextBorderColor(String textBorderColor) {
        this.textBorderColor = textBorderColor;
        return this;
    }

    public Integer getTextBorderWidth() {
        return textBorderWidth;
    }

    public Detail setTextBorderWidth(Integer textBorderWidth) {
        this.textBorderWidth = textBorderWidth;
        return this;
    }

    public String getTextShadowColor() {
        return textShadowColor;
    }

    public Detail setTextShadowColor(String textShadowColor) {
        this.textShadowColor = textShadowColor;
        return this;
    }

    public Integer getTextShadowBlur() {
        return textShadowBlur;
    }

    public Detail setTextShadowBlur(Integer textShadowBlur) {
        this.textShadowBlur = textShadowBlur;
        return this;
    }

    public Integer getTextShadowOffsetX() {
        return textShadowOffsetX;
    }

    public Detail setTextShadowOffsetX(Integer textShadowOffsetX) {
        this.textShadowOffsetX = textShadowOffsetX;
        return this;
    }

    public Integer getTextShadowOffsetY() {
        return textShadowOffsetY;
    }

    public Detail setTextShadowOffsetY(Integer textShadowOffsetY) {
        this.textShadowOffsetY = textShadowOffsetY;
        return this;
    }
}
