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

import org.youi.chart.echarts.util.ChartOptionUtils;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.model.axis.AxisX;
import org.youi.chart.engine.model.axis.AxisY;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.exception.BusException;

import java.util.ArrayList;
import java.util.List;

/**
 * xy轴线图option处理抽象类
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class XYOptionProcessor extends AbstractSeriesOptionProcessor{

    /**
     *
     * @param option
     * @param dataCube
     */
    protected void writeXYOption(IOption option, DataCube dataCube) {
        if(option.getxAxis()==null && option.getyAxis()==null){
            //如果没有轴线，生成默认的轴线
            writeDefaultAxisAndSeries(option,dataCube);
        }
        //写序列数据
        writeAllSeriesData(option,dataCube);
        //写图列
        writeLegend(option);
    }

    /**
     *
     * @param option
     * @param dataCube
     */
    protected void writeDefaultAxisAndSeries(IOption option, DataCube dataCube) {
        //计量维度
        Dimension measureDimension = DimensionUtils.findDimension(dataCube, DataQueryConstants.DIM_MEASURE_ID);
        if(measureDimension==null){
            throw new BusException("999999","缺少计量维度");
        }
        //分类轴
        Dimension categoryDimension = ChartOptionUtils.findCategoryDimension(dataCube,measureDimension);
        if(categoryDimension==null){
            throw new BusException("999999","缺少分类维度");
        }
        //
        option.setxAxis(buildAxisX(categoryDimension))
                .setyAxis(new AxisY[]{new AxisY()});
        //构建空序列
        measureDimension.getItems().forEach(item ->
                option.addSeries(buildSeries(item)));
    }

    /**
     *
     * @param categoryDimension
     * @return
     */
    private AxisX[] buildAxisX(Dimension categoryDimension) {
        AxisX axis = new AxisX();
        List<String> categoryTexts = new ArrayList<>();

        categoryDimension.getItems().forEach(item -> {
            categoryTexts.add(item.getText());
        });
        axis.setData(categoryTexts.toArray(new String[0]));
        return new AxisX[]{axis};
    }

}
