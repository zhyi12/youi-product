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
package org.youi.chart.engine.model.style;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ItemStyle extends AbstractStyle{

    private static final long serialVersionUID = -3533180045216962752L;

    private String borderColor;
    private Integer borderWidth;
    private String borderType;

    public String getBorderColor() {
        return borderColor;
    }

    public ItemStyle setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Integer getBorderWidth() {
        return borderWidth;
    }

    public ItemStyle setBorderWidth(Integer borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public String getBorderType() {
        return borderType;
    }

    public ItemStyle setBorderType(String borderType) {
        this.borderType = borderType;
        return this;
    }
}
