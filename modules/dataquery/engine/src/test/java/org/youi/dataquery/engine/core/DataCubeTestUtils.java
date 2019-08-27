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
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.*;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class DataCubeTestUtils {


    public static DataCube buildDataCube(){

        DataCube  dataCube = new DataCube();

        Dimension measureDimension = buildDimension(DataQueryConstants.DIM_MEASURE_ID,"计量");
        measureDimension.addItem(new Item("m001","数量1"));
        measureDimension.addItem(new Item("m002","数量2"));

        Dimension answerDimension = buildDimension("A","答案");
        answerDimension.addItem(new Item("item001","良好"));
        answerDimension.addItem(new Item("item002","一般"));
        answerDimension.addItem(new Item("item003","不佳"));

        dataCube.addDimension(measureDimension);
        dataCube.addDimension(answerDimension);


        List<Item>[] crossItemArray = DimensionUtils.expendedCrossColItems(dataCube.getDimensions());

        //
        for (List<Item> crossItems : crossItemArray) {
            DataItem dataItem = new DataItem();
            //数据的维度
            crossItems.forEach(crossItem->{
                dataItem.addDim(crossItem.getDimId(),crossItem.getId());
            });

            Double value = randomValue();
            DataValue dataValue = new DataValue(DimensionUtils.format(value));
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

    public static Double randomValue() {
        return  new Double(Math.random()*10000).intValue() / 100d;
    }
}
