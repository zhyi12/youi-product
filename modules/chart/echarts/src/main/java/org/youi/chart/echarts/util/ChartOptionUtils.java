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
package org.youi.chart.echarts.util;

import org.apache.commons.collections.CollectionUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class ChartOptionUtils {

    /**
     * 私有构造函数
     */
    private ChartOptionUtils(){
        //ignore
    }


    /**
     * 查找分类轴线的维度
     * @param dataCube
     * @param measureDimension
     * @return
     */
    public static Dimension findCategoryDimension(DataCube dataCube, Dimension measureDimension) {
        List<String> dimensionIds =  new ArrayList<>();
        if( CollectionUtils.isNotEmpty(dataCube.getHeaderItems())){
            dataCube.getHeaderItems().forEach(item -> dimensionIds.add(item.getDimId()));
        }
        dimensionIds.add(measureDimension.getId());
        for(Dimension dimension:dataCube.getDimensions()){
            if(!dimensionIds.contains(dimension.getId())){
                return dimension;
            }
        }
        return null;
    }
}
