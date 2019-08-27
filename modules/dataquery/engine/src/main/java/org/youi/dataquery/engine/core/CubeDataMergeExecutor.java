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

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.youi.framework.core.dataobj.cube.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 立方体合并
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class CubeDataMergeExecutor {

    /**
     * 合并立方体：根据新维度合并立方体集合为一个立方体
     * @param mergingCubes 立方体集合
     * @param addedDimension 新增加的维度
     * @return
     */
    public DataCube mergeCubes(List<DataCube> mergingCubes, Dimension addedDimension) {
        Assert.notNull(addedDimension,"新维度不能为空");//
        Assert.notEmpty(addedDimension.getItems(),"新维度项不能为空");
        Assert.notEmpty(mergingCubes,"待合并的立方体不能为空");
        Assert.isTrue(mergingCubes.size() == addedDimension.getItems().size(),"新维度维度项数量和待合并的立方体数量需要一致");

        DataCube dataCube = new DataCube();
        //
        DataCube firstDataCube = mergingCubes.get(0);//

        dataCube.setDimensions(new ArrayList<>(firstDataCube.getDimensions()));
        dataCube.addDimension(addedDimension);

        //合并数据
        int index = 0;
        for(DataCube mergingCube:mergingCubes){
            DataMap dataMap = mergingCube.getDatas();
            Item item = addedDimension.getItems().get(index++);
            item.setDimId(addedDimension.getId());
            //遍历立方体数据，在数据中增加新的维度项并写入到合并后的立方体
            dataMap.forEach((key, partDataItem)->{
                dataCube.addDataItem(buildMergedDataItem(item,partDataItem));
            });
        }
        return dataCube;
    }

    /**
     *
     * @param item
     * @param partDataItem
     * @return
     */
    private DataItem buildMergedDataItem(Item item, DataItem partDataItem) {
        DataItem dataItem = new DataItem();
        dataItem.setData(partDataItem.getData());//设置交叉格数据
        dataItem.addDim(item.getDimId(),item.getId());//增加交叉格的新维度项
        partDataItem.getDims().forEach(dataValueRef -> {
            dataItem.addDim(dataValueRef.getDimValue(),dataValueRef.getItemValue());
        });
        return dataItem;
    }
}
