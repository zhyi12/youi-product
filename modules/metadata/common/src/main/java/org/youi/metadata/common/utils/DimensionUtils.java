/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.metadata.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class DimensionUtils {


    /**
     * 获取指定的维度集合的维度项集合
     * @param items
     * @param dimIds
     * @return
     */
    public static Map<String, Item> findDimensionItems(List<Item> items, String[] dimIds){
        Map<String, Item> dimensionItems = new HashMap<>();
        items.forEach(item -> {
            if(item.getDimId()!=null && ArrayUtils.indexOf(dimIds,item.getDimId())>-1){
                dimensionItems.put(item.getDimId(),item);
            }
        });
        return dimensionItems;
    }

    /**
     * 一维展开cube的维度
     * @param dimensions
     * @return
     */
    public static List<Item>[] expendedCrossColItems(List<Dimension> dimensions){
        int dimensionCount = dimensions.size();
        int itemCount = 1;
        int spans = 1;
        List<Integer> spansList = new ArrayList<>();

        //计算占位
        for (int i = dimensionCount; i > 0; i--) {
            Dimension dimension = dimensions.get(i-1);
            if (i < dimensionCount) {
                spans = spans * (dimensions.get(i).getItems().size());
            }
            spansList.add(spans);
            itemCount = itemCount * dimension.getItems().size();
        }
        //构建维度项的一维交叉数组，方便数据计算
        List<Item> crossColItems[] = new List[itemCount];
        int index =0;
        double itemBlocks = 1;
        for(Dimension dimension:dimensions){
            //对维度进行笛卡尔组合
            for(int i=0;i<itemCount;i++){
                double itemIndex = (Math.floor(new Integer(i).doubleValue() / spansList.get(dimensionCount - index - 1))) % dimension.getItems().size();
                Item item = dimension.getItems().get(new Double(itemIndex).intValue());
                if(crossColItems[i]==null){
                    crossColItems[i] = new ArrayList<>();
                }
                item.setDimId(dimension.getId());
                crossColItems[i].add(item);
            }
            if(index<dimensionCount-1){
                itemBlocks = itemBlocks*dimension.getItems().size();
            }
            index++;
        }
        return crossColItems;
    }
}
