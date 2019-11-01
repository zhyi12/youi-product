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
package org.youi.chart.engine.model.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.youi.chart.engine.serializer.MultiTypePropertySerialize;

import java.util.List;

/**
 * 散点（气泡）数据
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ScatterData extends ChartData{

    private static final long serialVersionUID = 53011404693637952L;

    private String symbol;

    @JsonSerialize(using = MultiTypePropertySerialize.class)
    private String  symbolSize;//标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
    private Integer  symbolRotate;//标记的旋转角度
    private Boolean symbolKeepAspect;
    private List<Integer> symbolOffset;

    public String getSymbol() {
        return symbol;
    }

    public ScatterData setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getSymbolSize() {
        return symbolSize;
    }

    public ScatterData setSymbolSize(String symbolSize) {
        this.symbolSize = symbolSize;
        return this;
    }

    public Integer getSymbolRotate() {
        return symbolRotate;
    }

    public ScatterData setSymbolRotate(Integer symbolRotate) {
        this.symbolRotate = symbolRotate;
        return this;
    }

    public Boolean getSymbolKeepAspect() {
        return symbolKeepAspect;
    }

    public ScatterData setSymbolKeepAspect(Boolean symbolKeepAspect) {
        this.symbolKeepAspect = symbolKeepAspect;
        return this;
    }

    public List<Integer> getSymbolOffset() {
        return symbolOffset;
    }

    public ScatterData setSymbolOffset(List<Integer> symbolOffset) {
        this.symbolOffset = symbolOffset;
        return this;
    }
}
