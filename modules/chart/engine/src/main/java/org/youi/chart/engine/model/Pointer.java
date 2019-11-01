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
package org.youi.chart.engine.model;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Pointer implements Domain{

    private static final long serialVersionUID = 215580249553458341L;

    private Boolean show;
    private String length;
    private String  width;

    public Boolean getShow() {
        return show;
    }

    public Pointer setShow(Boolean show) {
        this.show = show;
        return this;
    }

    public String getLength() {
        return length;
    }

    public Pointer setLength(String length) {
        this.length = length;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public Pointer setWidth(String width) {
        this.width = width;
        return this;
    }
}
