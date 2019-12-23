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
package org.youi.dataworking.workingdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.dataworking.workingdata.service.WorkingCrossDataManager;
import org.youi.dataworking.workingdata.service.WorkingCrossDataQueryService;
import org.youi.dataworking.workingdata.util.WorkingCrossDataUtils;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.DataItem;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.ICrossItemProcessorDispatcher;
import org.youi.metadata.common.MetaDimensionType;
import org.youi.metadata.common.model.CrossItem;
import org.youi.metadata.common.model.CrossReport;
import org.youi.metadata.common.utils.DimensionUtils;

import java.util.*;

import static org.youi.dataworking.workingdata.util.WorkingCrossDataUtils.buildDataItem;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("workingCrossDataQueryService")
public class WorkingCrossDataQueryServiceImpl implements WorkingCrossDataQueryService {

    @Autowired(required = false)
    private ICrossItemProcessorDispatcher crossItemProcessorDispatcher;

    @Autowired
    private WorkingCrossDataManager workingCrossDataManager;//交叉数据服务类

    @Override
    @EsbServiceMapping(trancode="8006010371",caption="根据交叉表模型查询数据")
    public List<WorkingCrossData> queryByCrossReport(
            @ServiceParam(name = "periodId") String periodId,
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            CrossReport crossReport){

        List<WorkingCrossData> workingCrossDatas =
                WorkingCrossDataUtils.crossReportToWorkingDatas(periodId, areaId, crossReport, null,(crossItem)->{
                    if(crossItemProcessorDispatcher!=null){
                        //处理交叉维度
                        return crossItemProcessorDispatcher.process(crossItem);
                    }
                    return crossItem;
                });
        //查询数据并输出为map
        Map<String,WorkingCrossData> workingCrossDataMap = getFromDbAndConvertToMap(workingCrossDatas);

        return new ArrayList<>(workingCrossDataMap.values());
    }

    @Override
    @EsbServiceMapping(trancode="8006010372",caption="根据交叉项集合查询数据")
    public List<DataItem> queryByCrossItems(@ServiceParam(name = "periodId") String periodId,
                                        @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
                                        @DomainCollection(name = "crossItems",domainClazz = CrossItem.class) List<CrossItem> crossItems) {
        List<WorkingCrossData> workingCrossDatas = new ArrayList<>();//crossItems 转 WorkingCrossData
        if(!CollectionUtils.isEmpty(crossItems)){
            crossItems.forEach(crossItem -> {
                WorkingCrossData workingCrossData = buildWorkingCrossData(periodId,areaId,crossItem.getItems());
                if(workingCrossData!=null){
                    workingCrossDatas.add(workingCrossData);
                }
            });
        }

        Map<String,WorkingCrossData> workingCrossDataMap = getFromDbAndConvertToMap(workingCrossDatas);

        List<DataItem> dataItems = new ArrayList<>();//返回的数据项集合
        //构建dataItems集合
        workingCrossDataMap.forEach((id, workingCrossData) -> {
            dataItems.add(buildDataItem(workingCrossData));
        });
        return dataItems;
    }

    @Override
    @EsbServiceMapping(trancode = "8006010373", caption = "根据立方体集合查询数据")
    public List<DataCube> queryByDataCubes(@ServiceParam(name = "periodId") String periodId,
                                         @ServiceParam(name = "loginAreaId", pubProperty = "areaId") String areaId,
                                         @DomainCollection(name = "dataCubes",domainClazz = DataCube.class) List<DataCube> dataCubes) {
        if(!CollectionUtils.isEmpty(dataCubes)){
            dataCubes.forEach(dataCube -> processDataCubeDatas(periodId,areaId,dataCube));
        }
        return dataCubes;
    }

    /**
     * 处理立方体数据
     * @param periodId
     * @param areaId
     * @param dataCube
     */
    private void processDataCubeDatas(String periodId,String areaId,DataCube dataCube) {
        //如果维度中没有报告期或者行政区划，在立方体头项中加入
        this.processDataCubeHeaderItems(periodId, areaId, dataCube);
        //一维展开交叉项
        List<Item>[] crossItems = DimensionUtils.expendedCrossColItems(dataCube.getDimensions());

        List<WorkingCrossData> workingCrossDatas = new LinkedList<>();//交叉数据集合
        List<DataItem> dataItems = new LinkedList<>();//立方体中的数据项对象集合
        for(List<Item> crossItem:crossItems){
            DataItem dataItem = new DataItem();
            crossItem.forEach(item -> {
                dataItem.addDim(item.getDimId(),item.getId());
            });
            dataItems.add(dataItem);
            //
            if(crossItemProcessorDispatcher!=null){
                crossItem = crossItemProcessorDispatcher.process(crossItem);
            }
            WorkingCrossData workingCrossData = WorkingCrossDataUtils.buildPointCrossData(periodId,areaId,crossItem,0d);
            if(workingCrossData!=null){
                workingCrossDatas.add(workingCrossData);
            }
        }
        //查询立方体数据
        Map<String,WorkingCrossData> workingCrossDataMap = getFromDbAndConvertToMap(workingCrossDatas);

        int index = 0;
        for(WorkingCrossData workingCrossData:workingCrossDataMap.values()){
            DataItem dataItem = dataItems.get(index++);
            dataItem.setData(WorkingCrossDataUtils.buildDataItem(workingCrossData).getData());
            dataCube.addDataItem(dataItem);
        }
    }

    /**
     * 检查报告期和行政区划维度，如果不存在，在headerItems中加入
     */
    private void processDataCubeHeaderItems(String periodId,String areaId,DataCube dataCube){
        List headerItems = dataCube.getHeaderItems();
        if(headerItems==null){
            headerItems = new ArrayList();
        }
        //
        boolean hasArea = false,hasPeriod = false;

        for(Dimension dimension:dataCube.getDimensions()){
            if(MetaDimensionType.areas.name().equals(dimension.getId())){
                hasArea = true;
            }else if(MetaDimensionType.periods.name().equals(dimension.getId())||
                    MetaDimensionType.years.name().equals(dimension.getId())){
                hasPeriod = true;
            }
        }

        if(!hasArea){
            Item areaItem = new Item(areaId,areaId);
            areaItem.setDimId(MetaDimensionType.areas.name());
            headerItems.add(areaItem);
        }

        if(!hasPeriod){
            Item areaItem = new Item(periodId,periodId);
            areaItem.setDimId(MetaDimensionType.periods.name());
            headerItems.add(areaItem);
        }

        dataCube.setHeaderItems(headerItems);
    }

    /**
     * 从数据库查询交叉数据，并转换成map输出
     * @param workingCrossDatas
     * @return
     */
    private Map<String,WorkingCrossData> getFromDbAndConvertToMap(List<WorkingCrossData> workingCrossDatas){
        //转成Map
        Map<String,WorkingCrossData> workingCrossDataMap = new LinkedHashMap<>();
        workingCrossDatas.forEach(workingCrossData -> {
            workingCrossDataMap.put(workingCrossData.getId(),workingCrossData);
        });
        //从数据库查询数据
        List<WorkingCrossData> findings =  workingCrossDataManager.getWorkingCrossDataByIds(new ArrayList<>(workingCrossDataMap.keySet()));

        if(!CollectionUtils.isEmpty(findings)){
            //设置数据库查询到的数据到
            findings.forEach(workingCrossData -> {
                workingCrossDataMap.get(workingCrossData.getId()).setValue(workingCrossData.getValue()*workingCrossData.getUnitRate());
            });
        }
        return workingCrossDataMap;
    }

    /**
     * 生成空数据
     * @param periodId
     * @param areaId
     * @param items
     * @return
     */
    private WorkingCrossData buildWorkingCrossData(String periodId, String areaId, List<Item> items) {
        if(crossItemProcessorDispatcher!=null){
            items = crossItemProcessorDispatcher.process(items);
        }
        return WorkingCrossDataUtils.buildPointCrossData(periodId, areaId,items,0d);
    }
}
