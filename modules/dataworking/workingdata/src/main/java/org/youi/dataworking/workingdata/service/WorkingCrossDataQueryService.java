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
package org.youi.dataworking.workingdata.service;

import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.DataItem;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.model.CrossItem;
import org.youi.metadata.common.model.CrossReport;

import java.util.List;

/**
 * 交叉表数据查询
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface WorkingCrossDataQueryService {

    /**
     * 基于crossReport对象查询数据
     * @param periodId    报告期
     * @param areaId      行政区划
     * @param crossReport 交叉表样式
     * @return 返回交叉数据集合
     */
    @EsbServiceMapping(trancode = "8006010371", caption = "根据交叉表模型查询数据")
    List<WorkingCrossData> queryByCrossReport(
            @ServiceParam(name = "periodId") String periodId,
            @ServiceParam(name = "loginAreaId", pubProperty = "areaId") String areaId,
            CrossReport crossReport);

    /**
     * 基于交叉项集合查询数据
     *
     * @param periodId
     * @param areaId
     * @param crossItems
     * @return 返回DataItem集合
     */
    @EsbServiceMapping(trancode = "8006010372", caption = "根据交叉项集合查询数据")
    List<DataItem> queryByCrossItems(@ServiceParam(name = "periodId") String periodId,
                                 @ServiceParam(name = "loginAreaId", pubProperty = "areaId") String areaId,
                                 @DomainCollection(name = "crossItems", domainClazz = CrossItem.class) List<CrossItem> crossItems);

    /**
     * 基于数据立方体集合查询交叉数据
     * @param periodId
     * @param areaId
     * @param dataCubes 数据立方体
     * @return 返回带数据的立方体集合
     */
    @EsbServiceMapping(trancode = "8006010373", caption = "根据立方体集合查询数据")
    List<DataCube> queryByDataCubes(@ServiceParam(name = "periodId") String periodId,
                                  @ServiceParam(name = "loginAreaId", pubProperty = "areaId") String areaId,
                                  List<DataCube> dataCubes);
}
