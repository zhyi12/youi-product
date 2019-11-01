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

import org.apache.commons.lang.StringUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class DataCubeMockUtils {

    /**
     *
     * @return
     */
    public static DataCube buildDataCubeWithPeriodHeader(){
        DataCube dataCube = buildDataCube();
        return addPeriodHeader(dataCube);
    }

    public static DataCube addPeriodHeader(DataCube dataCube){
        List<Item> headerItems = new ArrayList<>();
        Item headerItem = new Item("P001","2019年10月");
        headerItem.setDimId("P");
        headerItems.add(headerItem);
        dataCube.setHeaderItems(headerItems);
        return dataCube;
    }

    public static DataCube buildDataCube() {
        DataCube dataCube = new DataCube();
        dataCube.addDimension(buildDimension(DataQueryConstants.DIM_MEASURE_ID,"指标",0));
        dataCube.addDimension(buildDimension("P","报告期",0));
        dataCube.addDimension(buildDimension("G001","企业规模",0));
        //构造数据
        buildDataCubeData(dataCube);
        return dataCube;
    }

    /**
     * 一个计量项
     * @return
     */
    public static DataCube buildDataCubePie(String groupId,String groupText) {
        DataCube dataCube = new DataCube();
        dataCube.addDimension(buildDimension(DataQueryConstants.DIM_MEASURE_ID,"指标",1));
        dataCube.addDimension(buildDimension("P","报告期",1));
        dataCube.addDimension(buildDimension(groupId,groupText,0));
        //构造数据
        buildDataCubeData(dataCube);
        return addPeriodHeader(dataCube);
    }

    /**
     *
     * @param dataCube
     */
    private static void buildDataCubeData(DataCube dataCube) {
        List<Item>[] expendedCrossColItems = DimensionUtils.expendedCrossColItems(dataCube.getDimensions());
        for(List<Item> crossColItems:expendedCrossColItems){
            dataCube.addDataItem(buildDataItem(crossColItems));
        }
    }

    /**
     *
     * @param crossColItems
     * @return
     */
    private static DataItem buildDataItem(List<Item> crossColItems) {
        DataItem dataItem = new DataItem();
        crossColItems.forEach(crossColItem->dataItem.addDim(crossColItem.getDimId(),crossColItem.getId()));
        Double value = randomValue();
        DataValue dataValue = new DataValue(DimensionUtils.format(value));
        dataValue.setValue(value);
        dataItem.setData(dataValue);
        return dataItem;
    }

    private static Double randomValue() {
        return Math.ceil(Math.random()*10000)/100;
    }

    /**
     * 构建维度
     * @param id
     * @param text
     * @return
     */
    private static Dimension buildDimension(String id, String text,int maxCount) {
        Dimension dimension = new Dimension();
        dimension.setId(id);
        dimension.setText(text);

        int count = Double.valueOf(Math.random()*1000000 % 20).intValue();
        if(count<5)count = 5;

        if(maxCount>0){
            count = Math.min(maxCount,count);
        }

        for(int i=1;i<=count;i++){
            dimension.addItem(new Item(id+ StringUtils.leftPad(Integer.valueOf(i).toString(),3,"0"),text+i));
        }
        return dimension;
    }
}
