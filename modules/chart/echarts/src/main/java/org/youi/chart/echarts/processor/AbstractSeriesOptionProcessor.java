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

import org.apache.commons.collections.CollectionUtils;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.IComplexDataSeries;
import org.youi.chart.engine.ISeries;
import org.youi.chart.engine.model.Legend;
import org.youi.chart.engine.model.data.ChartData;
import org.youi.chart.engine.model.series.AbstractSeries;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.utils.CalculateItemUtils;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.exception.BusException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class AbstractSeriesOptionProcessor<T> {

    /**
     * 构建无数据的序列
     * @param option
     * @param dataCube
     */
    protected void writeDefaultSeries(IOption option, DataCube dataCube) {
        Dimension measureDimension = DimensionUtils.findDimension(dataCube, DataQueryConstants.DIM_MEASURE_ID);
        if(measureDimension==null){
            throw new BusException("999999","缺少计量维度");
        }
        //构建空序列
        measureDimension.getItems().forEach(item ->
                option.addSeries(buildSeries(item)));
    }

    /**
     *
     * @param item
     * @return
     */
    protected abstract ISeries buildSeries(Item item);
    /**
     *
     * @param option
     */
    protected void writeLegend(IOption option) {
        //图列,多序列时，需要配置图列的data
        if(CollectionUtils.isNotEmpty(option.getSeries()) && option.getSeries().size()>1){
            Legend legend = new Legend();
            option.getSeries().forEach(iSeries -> {
                legend.addData(iSeries.getName());
            });
            option.setLegend(legend);
        }
    }

    /**
     * 写入多序列数据
     * @param option
     * @param dataCube
     */
    protected void writeAllSeriesData(IOption option, DataCube dataCube) {
        //展开维度数据
        List<Item>[] crossColItems = DimensionUtils.expendedCrossColItems(dataCube.getDimensions());
        for(ISeries series:option.getSeries()){
            Item seriesItem = new Item(series.getId(),"");
            seriesItem.setDimId(DataQueryConstants.DIM_MEASURE_ID);
            //写序列数据
            writeOneSeriesData(series,dataCube,crossColItems,seriesItem);
        }
    }

    /**
     * 写入序列数据
     * @param series
     * @param dataCube
     * @param crossColItems
     * @param seriesItem
     */
    protected void writeOneSeriesData(ISeries series,DataCube dataCube,List<Item>[] crossColItems,Item seriesItem) {
        List<String> matchItemIds = buildMatchItemIds(dataCube,seriesItem);
        for (List<Item> crossColItem : crossColItems) {//
            if (allMatched(crossColItem,matchItemIds)) {//匹配并获取数据
                if(IComplexDataSeries.class.isAssignableFrom(series.getClass())){
                    //获取带中文描述的item
                    seriesItem = findSeriesItem(crossColItem,seriesItem);
                    ((AbstractSeries)series).addData(buildChartData(seriesItem,findDataValue(dataCube, crossColItem)));
                }else{
                    ((AbstractSeries)series).addData(findDataValue(dataCube, crossColItem));
                }
            }
        }
    }

    /**
     *
     * @param dataCube
     * @param seriesItem
     * @return
     */
    protected List<String> buildMatchItemIds(DataCube dataCube,Item seriesItem){
        List<String> matchItemIds = new ArrayList<>();
        if(dataCube.getHeaderItems()!=null){
            dataCube.getHeaderItems().forEach(item -> matchItemIds.add(item.getDimId()+"_"+item.getId()));
        }
        matchItemIds.add(seriesItem.getDimId()+"_"+seriesItem.getId());
        return matchItemIds;
    }

    private Item findSeriesItem(List<Item> crossColItem, Item seriesItem){
        for(Item item:crossColItem){
            if(item.getDimId().equals(seriesItem.getDimId()) && item.getId().equals(seriesItem.getId())){
                return item;
            }
        }
        return seriesItem;
    }

    /**
     * @param dataValue
     * @return
     */
    protected ChartData buildChartData(Item seriesItem, Double dataValue) {
        return new ChartData().setName(seriesItem.getText()).setValue(dataValue);
    }

    /**
     *
     * @param crossColItem
     * @param matchItems
     * @return
     */
    protected boolean allMatched(List<Item> crossColItem, List<String> matchItems) {
        int matchCount = 0;
        for(Item item:crossColItem){
            if(matchItems.contains(item.getDimId()+"_"+item.getId())){
                matchCount++;
            }
        }
        return matchCount == matchItems.size();
    }

    /**
     *
     * @param dataCube
     * @param crossColItem
     * @return
     */
    protected Double findDataValue(DataCube dataCube, List<Item> crossColItem) {
        String dataKey = CalculateItemUtils.buildDataItem(crossColItem).buildKey();
        if(dataCube.getDatas().containsKey(dataKey)){
            return CalculateItemUtils.findDataItemValue(dataCube.getDatas().get(dataKey));
        }
        return 0d;
    }
}
