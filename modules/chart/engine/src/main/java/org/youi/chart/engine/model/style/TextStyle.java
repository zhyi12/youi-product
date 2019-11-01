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
package org.youi.chart.engine.model.style;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class TextStyle implements Domain {

    private static final long serialVersionUID = -8437244929871401373L;

    private String color;
    private String fontStyle;
    /**
     * 文字字体的粗细
     * <p>
     * 可选：
     * <p>
     * 'normal'
     * 'bold'
     * 'bolder'
     * 'lighter'
     * 100 | 200 | 300 | 400...
     */
    private String fontWeight;
    private String fontFamily;
    private Integer fontSize;
    private Integer lineHeight;
    private Integer width;
    private Integer height;
    private String textBorderColor;
    private Integer textBorderWidth;
    private String textShadowColor;
    private Integer textShadowBlur;
    private Integer textShadowOffsetX;
    private Integer textShadowOffsetY;

    public String getColor() {
        return color;
    }

    public TextStyle setColor(String color) {
        this.color = color;
        return this;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public TextStyle setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public TextStyle setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
        return this;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public TextStyle setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public TextStyle setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public Integer getLineHeight() {
        return lineHeight;
    }

    public TextStyle setLineHeight(Integer lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public TextStyle setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public TextStyle setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public String getTextBorderColor() {
        return textBorderColor;
    }

    public TextStyle setTextBorderColor(String textBorderColor) {
        this.textBorderColor = textBorderColor;
        return this;
    }

    public Integer getTextBorderWidth() {
        return textBorderWidth;
    }

    public TextStyle setTextBorderWidth(Integer textBorderWidth) {
        this.textBorderWidth = textBorderWidth;
        return this;
    }

    public String getTextShadowColor() {
        return textShadowColor;
    }

    public TextStyle setTextShadowColor(String textShadowColor) {
        this.textShadowColor = textShadowColor;
        return this;
    }

    public Integer getTextShadowBlur() {
        return textShadowBlur;
    }

    public TextStyle setTextShadowBlur(Integer textShadowBlur) {
        this.textShadowBlur = textShadowBlur;
        return this;
    }

    public Integer getTextShadowOffsetX() {
        return textShadowOffsetX;
    }

    public TextStyle setTextShadowOffsetX(Integer textShadowOffsetX) {
        this.textShadowOffsetX = textShadowOffsetX;
        return this;
    }

    public Integer getTextShadowOffsetY() {
        return textShadowOffsetY;
    }

    public TextStyle setTextShadowOffsetY(Integer textShadowOffsetY) {
        this.textShadowOffsetY = textShadowOffsetY;
        return this;
    }
}
