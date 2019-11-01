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
package org.youi.chart.echarts.processor;

import org.springframework.stereotype.Component;
import org.youi.chart.engine.IChartOptionProcessor;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.ISeries;
import org.youi.chart.engine.model.series.BarSeries;
import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Item;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class BarOptionProcessor extends XYOptionProcessor implements IChartOptionProcessor {

    @Override
    public boolean supports(String chartType) {
        return ChartType.Bar.name().equals(chartType);
    }

    @Override
    public void writeOption(IOption option, DataCube... dataCube) {
        this.writeXYOption(option,dataCube[0]);
    }

    @Override
    protected ISeries buildSeries(Item item) {
        return new BarSeries().setId(item.getId()).setName(item.getText());
    }
}
