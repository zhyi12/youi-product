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
package org.youi.dataquery.engine.core;

import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.utils.CubeDimensionUtils;
import org.youi.framework.core.dataobj.cube.*;
import org.youi.metadata.common.utils.DimensionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class DataCubeTestUtils {

    public static final String DIM_GROUP_A = "A";

    public static DataCube buildDataCube(){

        DataCube  dataCube = new DataCube();

        Dimension measureDimension = buildDimension(DataQueryConstants.DIM_MEASURE_ID,"计量");
        measureDimension.addItem(new Item("m001","项目1"));
        measureDimension.addItem(new Item("m002","项目2"));
        measureDimension.addItem(new Item("m003","项目3"));
        measureDimension.addItem(new Item("m004","项目4"));

        Dimension answerDimension = buildDimension(DIM_GROUP_A,"答案");
        answerDimension.addItem(new Item("item001","良好"));
        answerDimension.addItem(new Item("item002","一般"));
        answerDimension.addItem(new Item("item003","不佳"));

        dataCube.addDimension(measureDimension);
        dataCube.addDimension(answerDimension);


        List<Item>[] crossItemArray = CubeDimensionUtils.expendedCrossColItems(dataCube.getDimensions());

        //
        for (List<Item> crossItems : crossItemArray) {
            DataItem dataItem = new DataItem();
            //数据的维度
            crossItems.forEach(crossItem->{
                dataItem.addDim(crossItem.getDimId(),crossItem.getId());
            });

            Double value = randomValue();
            DataValue dataValue = new DataValue(CubeDimensionUtils.format(value));
            dataValue.setValue(value);
            dataItem.setData(dataValue);
            dataCube.addDataItem(dataItem);
        }

        return dataCube;
    }

    public static Dimension buildDimension(String id, String text) {
        Dimension dimension = new Dimension();
        dimension.setId(id);
        dimension.setText(text);
        return dimension;
    }

    public static void printDataCube(DataCube dataCube){

        List<Dimension> dimensions = new ArrayList<>(dataCube.getDimensions());
        //打印立方体
        Dimension mainDimension = dimensions.remove(0);

        List<Item>[] crossItems =  DimensionUtils.expendedCrossColItems(dimensions);

        System.out.print("项目   ");
        for(List<Item> crossItem:crossItems){
            System.out.print(crossItem.get(crossItem.size()-1).getText()+"  ");
        }
        System.out.println("");
        //
        for(Item mainItem:mainDimension.getItems()){
            System.out.print(mainItem.getText()+"   ");
            for(List<Item> crossItem:crossItems){
                DataItem dataItem = new DataItem();
                dataItem.addDim(mainItem.getDimId(),mainItem.getId());
                crossItem.forEach(item -> {
                    dataItem.addDim(item.getDimId(),item.getId());
                });

                if(dataItem!=null){
                    String dataKey = dataItem.buildKey();
                    DataItem crossDataItem = dataCube.getDatas().get(dataKey);

                    if(crossDataItem!=null){
                        System.out.print(crossDataItem.getData().getStrValue()+"   ");
                    }
                }


            }
            System.out.println("");
        }
    }

    public static Double randomValue() {
        return  new Double(Math.random()*10000).intValue() / 100d;
    }
}
