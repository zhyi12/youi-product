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
package org.youi.chart.echarts;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.chart.engine.IChartOptionProcessor;
import org.youi.chart.engine.IChartOptionWriter;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.model.ChartOption;
import org.youi.chart.engine.model.Tooltip;
import org.youi.framework.core.dataobj.cube.DataCube;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class EchartsOptionWriter implements IChartOptionWriter,ApplicationContextAware{

    private List<IChartOptionProcessor> chartOptionProcessors;

    /**
     *
     * 根据数据立方体输出图表
     * @return
     */
    public IOption write(String chartType,IOption option,DataCube... dataCube){
        IOption chartOption = option==null?buildDefaultChartOption():option;//防止空对象
        //fix 图像信息
        if(!CollectionUtils.isEmpty(chartOptionProcessors)){
            for(IChartOptionProcessor chartOptionProcessor:chartOptionProcessors){
                if(chartOptionProcessor.supports(chartType)){
                    chartOptionProcessor.writeOption(chartOption,dataCube);
                    break;
                }
            }
        }
        return chartOption;
    }

    public ChartOption buildDefaultChartOption(){
        ChartOption chartOption = new ChartOption();
        chartOption.setTooltip(new Tooltip().setTrigger("axis"));
        return chartOption;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initChartOptionProcessors(applicationContext);
    }

    private void initChartOptionProcessors(ApplicationContext context) {
        if (chartOptionProcessors == null) {
            Map<String, IChartOptionProcessor> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IChartOptionProcessor.class, true, false);
            if (adapterBeans != null) {
                chartOptionProcessors = new ArrayList<>(adapterBeans.values());
                chartOptionProcessors.sort(new OrderComparator());
            }
        }
    }
}
