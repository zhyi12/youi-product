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
import org.springframework.util.CollectionUtils;
import org.youi.chart.echarts.util.ChartOptionUtils;
import org.youi.chart.engine.IChartOptionProcessor;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.ISeries;
import org.youi.chart.engine.model.Legend;
import org.youi.chart.engine.model.Tooltip;
import org.youi.chart.engine.model.series.PieSeries;
import org.youi.chart.engine.model.value.ChartType;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class PieOptionProcessor extends AbstractSeriesOptionProcessor implements IChartOptionProcessor{
    @Override
    public boolean supports(String chartType) {
        return ChartType.Pie.name().equals(chartType);
    }

    @Override
    public void writeOption(IOption option, DataCube... dataCubes) {
        if(CollectionUtils.isEmpty(option.getSeries())){
            //初始化序列
            for (DataCube dataCube : dataCubes) {
                writeDefaultSeries(option,dataCube);
            }
            //设置trigger
            option.setTooltip(new Tooltip().setTrigger("item").setFormatter("{a} <br/>{b}: {c} ({d}%)"))
                    .setLegend(new Legend().setOrient("vertical").setLeft("10"));
        }

        if(option.getLegend()!=null){
            option.getLegend().setData(new ArrayList<>());//置空图列
        }

        //填充序列数据和图例
        int cubeCount = dataCubes.length;
        int cubeIndex = 0;
        for (DataCube dataCube : dataCubes) {
            writePieData(option,dataCube,cubeCount,cubeIndex++);
        }
        option.setxAxis(null).setyAxis(null);//删除轴线
    }

    /**
     *
     * @param option
     * @param dataCube
     */
    protected  void writeDefaultSeries(IOption option, DataCube dataCube){
        Dimension measureDimension = DimensionUtils.findDimension(dataCube, DataQueryConstants.DIM_MEASURE_ID);
        //分类轴
        Dimension categoryDimension = ChartOptionUtils.findCategoryDimension(dataCube,measureDimension);
        //使用category构建饼图序列
        option.addSeries(buildSeries(new Item(categoryDimension.getId(),categoryDimension.getText())));
    }

    /**
     *
     * @param option
     * @param dataCube
     */
    private void writePieData(IOption option, DataCube dataCube,int cubeCount,int cubeIndex) {
        Dimension measureDimension = DimensionUtils.findDimension(dataCube, DataQueryConstants.DIM_MEASURE_ID);
        //分类轴
        Dimension categoryDimension = ChartOptionUtils.findCategoryDimension(dataCube,measureDimension);

        Item item = measureDimension.getItems().get(0);

        PieSeries pieSeries = null;

        //
        for(ISeries series:option.getSeries()){
            if(categoryDimension.getId().equals(series.getId())){
                pieSeries = (PieSeries)series;
                break;
            }
        }

        if(pieSeries!=null){
            pieSeries.setRadius(buildRadius(cubeCount,cubeIndex));
            //将分组维度写入序列数据
            for(Item categoryItem:categoryDimension.getItems()){
                List<Item> items = new ArrayList<>();
                if(!CollectionUtils.isEmpty(dataCube.getHeaderItems())){
                    items.addAll(dataCube.getHeaderItems());
                }
                items.add(categoryItem);
                items.add(item);
                pieSeries.addData(buildChartData(categoryItem,findDataValue(dataCube,items)));

                option.getLegend().addData(categoryItem.getText());
            }
        }
    }

    /**
     * 饼图的内外半径设置
     * @param cubeCount
     * @param cubeIndex
     * @return
     */
    private String[] buildRadius(int cubeCount, int cubeIndex) {
        //数组的第一项是内半径，第二项是外半径
        if(cubeCount==1){
            return new String []{"0%","50%"};
        }else if(cubeCount==2){
            if(cubeIndex==0){
                return new String []{"0%","20%"};
            }else if(cubeIndex==1){
                return new String []{"30%","65%"};
            }
        }else if(cubeCount==3){
            if(cubeIndex==0){
                return new String []{"0%","20%"};
            }else if(cubeIndex==1){
                return new String []{"30%","55%"};
            }else if(cubeIndex==1){
                return new String []{"60%","85%"};
            }
        }
        return new String[]{};
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    protected ISeries buildSeries(Item item) {
        return new PieSeries().setId(item.getId()).setName(item.getText());
    }
}
