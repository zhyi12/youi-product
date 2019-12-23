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
package org.youi.dataworking.workingdata.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.framework.core.dataobj.cube.DataItem;
import org.youi.framework.core.dataobj.cube.DataValue;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.MetaDimensionType;
import org.youi.metadata.common.model.BlockArea;
import org.youi.metadata.common.model.CrossReport;
import org.youi.metadata.common.model.DimensionArea;
import org.youi.metadata.common.model.RowText;
import org.youi.metadata.common.utils.DimensionUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class WorkingCrossDataUtils {

    /**
     * @param periodId
     * @param areaId
     * @param crossItem 交叉项
     * @param value 值
     */
    public static WorkingCrossData buildPointCrossData(String periodId, String areaId, List<Item> crossItem, Double value) {
        boolean hasAccDimension = false;

        WorkingCrossData workingCrossData = new WorkingCrossData();
        workingCrossData.setValue(value);
        for(Item item:crossItem){
            if(hasAccDimension(item)){
                hasAccDimension = true;
            }
            if(MetaDimensionType.periods.name().equals(item.getDimId())){
                workingCrossData.setPeriodId(item.getId());//报告期
            }else if(MetaDimensionType.areas.name().equals(item.getDimId())){
                workingCrossData.setAreaId(item.getId());//行政区划
            }else if(MetaDimensionType.attrs.name().equals(item.getDimId())){
                workingCrossData.setAttrId(item.getId());//属性
            }else if(MetaDimensionType.indicators.name().equals(item.getDimId())){
                workingCrossData.setIndicatorId(item.getId());//指标
            }else if(MetaDimensionType.accs.name().equals(item.getDimId())){
                workingCrossData.setAcc("1".equals(item.getId())?Boolean.TRUE:Boolean.FALSE);//属性
            }else{//其他分组项
                workingCrossData.addGroupItem(item.getDimId(),item.getId());
            }
        }

        if(StringUtils.isEmpty(workingCrossData.getAreaId())){
            workingCrossData.setAreaId(areaId);
        }

        if(StringUtils.isEmpty(workingCrossData.getPeriodId())){
            workingCrossData.setPeriodId(periodId);
        }

        if(hasAccDimension){
            workingCrossData.setAcc(true);
        }
        //TODO 基于制度的指标设置数据换算率
//        workingCrossData.setUnitRate(1d);
        //生成数据主键
        workingCrossData.buildKey();
        return workingCrossData;
    }

    /**
     *
     * @param workingCrossData
     * @return
     */
    public static DataItem buildDataItem(WorkingCrossData workingCrossData) {
        DataItem dataItem = new DataItem();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        DataValue data = new DataValue(numberFormat.format(workingCrossData.getValue()));
        data.setValue(workingCrossData.getValue());

        dataItem.setData(data);
        return dataItem;
    }

    /**
     *
     * @param crossReport
     */
    public static void processAreaDimensionId(CrossReport crossReport) {
        //重新设置dimensionId 为group
        for(BlockArea blockArea:crossReport.getSlaveAreas()){
            for(DimensionArea dimensionArea:blockArea.getDimensions()){
                dimensionArea.setId(dimensionArea.getGroup()+"s");
            }
        }
        for(BlockArea blockArea:crossReport.getMainAreas()){
            for(DimensionArea dimensionArea:blockArea.getDimensions()){
                dimensionArea.setId(dimensionArea.getGroup()+"s");
            }
        }
    }

    public static List<WorkingCrossData> crossReportToWorkingDatas(String periodId, String areaId, CrossReport crossReport,
                                                                   List<RowText> rowTexts, Function<List<Item>,List<Item>> crossItemProcessor){
        List<WorkingCrossData> workingDatas = new ArrayList<>();
        if(crossReport.canCalculate()){//判断报表是否可计算
            WorkingCrossDataUtils.processAreaDimensionId(crossReport);//设置区域维度的维度ID= group+"s"
            //从左到右，从上到下
            Map<String,List<Item>[]> mainCrossItemsMap = buildMainCrossItemsMap(crossReport.getMainAreas());
            //遍历主栏、宾栏，形成交叉的数据区域
            int startColIndex = 0,startRowIndex = 0;
            for(BlockArea slaveArea:crossReport.getSlaveAreas()){//遍历宾栏块
                startRowIndex = 0;
                List<Item>[] slaveCrossItems = DimensionUtils.expendedCrossColItems(new ArrayList<>(slaveArea.getDimensions()));
                for(BlockArea mainArea:crossReport.getMainAreas()){//遍历主栏块
                    List<Item>[] mainCrossItems = mainCrossItemsMap.get(mainArea.getId());
                    List<WorkingCrossData> cubeWorkingCrossDatas =
                            buildCubeWorkingCrossDatas(periodId,areaId,crossReport,mainArea,slaveArea,mainCrossItems,slaveCrossItems,
                                    rowTexts,startRowIndex,startColIndex,crossItemProcessor);
                    if(!CollectionUtils.isEmpty(cubeWorkingCrossDatas)){
                        workingDatas.addAll(cubeWorkingCrossDatas);
                    }
                    startRowIndex += mainCrossItems.length;
                }
                startColIndex+=slaveCrossItems.length;
            }
        }
        return workingDatas;
    }

    /**
     * 立方体数据转换为数据点
     *
     *
     * @param periodId
     * @param areaId
     * @param crossReport
     * @param mainArea
     * @param slaveArea
     * @param mainCrossItems
     * @param slaveCrossItems
     * @param rowTexts
     * @param startRowIndex
     * @param startColIndex
     * @return
     */
    private static List<WorkingCrossData> buildCubeWorkingCrossDatas(
            String periodId, String areaId, CrossReport crossReport,
            BlockArea mainArea,
            BlockArea slaveArea,
            List<Item>[] mainCrossItems,
            List<Item>[] slaveCrossItems,
            List<RowText> rowTexts, int startRowIndex, int startColIndex,Function<List<Item>,List<Item>> crossItemProcessor) {

        List<WorkingCrossData> workingCrossDatas = new ArrayList<>();

        int rowIndex = 0,colIndex;
        for(List<Item> mainCrossItem:mainCrossItems){
            rowIndex++;
            colIndex=0;
            for(List<Item> slaveCrossItem:slaveCrossItems){
                List<Item> crossItem = findCrossItem(crossReport,mainArea,slaveArea,mainCrossItem,slaveCrossItem);
                //
                crossItem = crossItemProcessor.apply(crossItem);
                WorkingCrossData workingCrossData = null;
                try {
                    workingCrossData = buildPointCrossData(periodId,areaId,crossItem,
                            getDataFromRowTexts(rowTexts,startRowIndex+rowIndex,startColIndex+colIndex));
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                if(workingCrossData!=null){
                    workingCrossData.setRowIndex(startRowIndex+rowIndex);
                    workingCrossData.setColIndex(startColIndex+colIndex);
                    workingCrossDatas.add(workingCrossData);
                }
                colIndex++;
            }
        }
        return workingCrossDatas;
    }

    /**
     * 从cube中获取交叉的维度项数据
     *
     * @param crossReport
     * @param mainArea
     * @param slaveArea
     * @param mainCrossItem
     * @param slaveCrossItem
     * @return
     */
    private static List<Item> findCrossItem(
            CrossReport crossReport,
            BlockArea mainArea, BlockArea slaveArea,
            List<Item> mainCrossItem, List<Item> slaveCrossItem) {
        List<Item> crossItem = new ArrayList<>();
        //添加表头维度
        if(!CollectionUtils.isEmpty(crossReport.getHeaderItems())){
            crossItem.addAll(crossReport.getHeaderItems());
        }
        //添加主栏公共维度
        if(!CollectionUtils.isEmpty(mainArea.getPubItems())){
            crossItem.addAll(mainArea.getPubItems());
        }
        //添加宾栏公共维度
        if(!CollectionUtils.isEmpty(slaveArea.getPubItems())){
            crossItem.addAll(slaveArea.getPubItems());
        }
        //添加主栏行展开维度
        crossItem.addAll(mainCrossItem);
        //添加宾栏列展开展开
        crossItem.addAll(slaveCrossItem);
        return crossItem;
    }

    /**
     * 获取主栏块展开维度项集合
     * @param mainAreas
     * @return
     */
    private static Map<String, List<Item>[]> buildMainCrossItemsMap(List<BlockArea> mainAreas) {
        Map<String,List<Item>[]> mainCrossItemsMap = new HashMap<>();
        for(BlockArea mainArea:mainAreas){
            List<Item>[] crossItems = DimensionUtils.expendedCrossColItems(new ArrayList<>(mainArea.getDimensions()));
            if(ArrayUtils.isNotEmpty(crossItems)){
                mainCrossItemsMap.put(mainArea.getId(),crossItems);
            }
        }
        return mainCrossItemsMap;
    }

    /**
     *
     * @param rowTexts
     * @param rowIndex
     * @param colIndex
     * @return
     */
    private static Double getDataFromRowTexts(List<RowText> rowTexts, int rowIndex, int colIndex) {
        if(rowTexts!=null && rowIndex<=rowTexts.size()){
            String text = rowTexts.get(rowIndex-1).getTexts()[colIndex];
            if(StringUtils.isNotEmpty(text)){
                try {
                    return Double.parseDouble(text);
                } catch (NumberFormatException e) {
                    //ignore
                }
            }
        }
        return 0d;
    }

    /**
     * 是否累计值
     * @param item
     * @return
     */
    private static boolean hasAccDimension(Item item){
        return MetaDimensionType.accs.getKey().equals(item.getDimId())
                ||(MetaDimensionType.periods.getKey().equals(item.getDimId()) && item.getLevel()==1);//level 1 代表累计报告期
    }
}
