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

import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.model.CubeRowData;
import org.youi.dataquery.engine.utils.CubeDimensionUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.DataItem;
import org.youi.framework.core.dataobj.cube.DataValue;
import org.youi.framework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class CubeRowDataWriter {

    /**
     * 填充cube数据
     * @param dataCube
     * @param cubeRowDatas
     */
    public void write(DataCube dataCube,List<CubeRowData> cubeRowDatas) {
        if(!CollectionUtils.isEmpty(cubeRowDatas)){
            cubeRowDatas.forEach(cubeRowData -> {
                writeDataItems(dataCube,cubeRowData);
            });
        }
    }

    /**
     * 写入数据行到cube的数据模型中
     * @param dataCube
     * @param cubeRowData
     */
    protected void writeDataItems(DataCube dataCube,CubeRowData cubeRowData){
        if(CollectionUtils.isEmpty(cubeRowData.getMeasures())){
            return;
        }
        Map<String,String> dims = new HashMap<>();
        //有目录项
        if(StringUtils.isNotEmpty(cubeRowData.getCatalogItemId())){
            dims.put(DataQueryConstants.DIM_CATALOG_ID,cubeRowData.getCatalogItemId());
        }
        //汇总的分组项
        if(!CollectionUtils.isEmpty(cubeRowData.getGroups())){
            dims.putAll(cubeRowData.getGroups());
        }
        //遍历计量项
        cubeRowData.getMeasures().forEach((indicatorId,value)->{
            writeIndicatorDataItem(dataCube,indicatorId,value,dims);
        });
    }

    /**
     * 写入立方体交叉单元格数据
     * @param dataCube
     * @param indicatorId 计量维度中的数据指标ID
     * @param value 数据值
     * @param dims
     */
    protected void writeIndicatorDataItem(DataCube dataCube,String indicatorId, Double value, Map<String,String> dims){
        DataItem dataItem = new DataItem();
        //处理交叉维度
        dims.forEach((dimId,itemId)->{
            dataItem.addDim(dimId,itemId);
        });
        //处理计量指标项
        dataItem.addDim(DataQueryConstants.DIM_MEASURE_ID,indicatorId);
        //数据
        DataValue dataValue = new DataValue(CubeDimensionUtils.format(value));
        dataValue.setValue(value);//设置数字值

        dataItem.setData(dataValue);
        dataCube.addDataItem(dataItem);//加入dataItem到cube中
    }
}
