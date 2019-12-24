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

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.model.CalculateItem;
import org.youi.dataquery.engine.utils.CalculateItemUtils;
import org.youi.dataquery.engine.utils.CubeDimensionUtils;
import org.youi.framework.core.dataobj.cube.*;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.model.ValueItem;

import java.util.*;

import static org.youi.dataquery.engine.DataQueryConstants.*;

/**
 *
 * 立方体计算
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CubeDataCalculator {


    private  static ExpressionParser parser = new SpelExpressionParser();

    /**
     * 计算维度中的表达式数据
     * @param dataCube
     * @param dimId
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId){
        return doCalculateDimension(dataCube,dimId,null,null,null);
    }

    /**
     *
     * @param dataCube 立方体
     * @param dimId 计算维度的ID
     * @param calculateItems 计算项
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId, List<CalculateItem> calculateItems){
        return doCalculateDimension(dataCube,dimId,calculateItems,null,null);
    }

    /**
     * 实现排名、占比类型的计算计算
     * @param dataCube 立方体
     * @param dimId 计算维度的ID
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId,
                                       String refDimId,List<CalculateItem> refCalculateItems){
        return doCalculateDimension(dataCube,dimId,null,refDimId,refCalculateItems);
    }

    /**
     * 清理立方体无用数据
     * @param dataCube
     */
    public void cleanCubeDatas(DataCube dataCube){
        if(dataCube.getDatas()==null)return;
        List<Item>[] crossColItems = CubeDimensionUtils.expendedCrossColItems(dataCube.getDimensions());
        List<String> dataKeys = new ArrayList<>();

        //交叉维度生成data keys集合
        for(List<Item> crossColItem:crossColItems){
            DataItem dataItem = new DataItem();
            List<Item> crossItems = new ArrayList<>(crossColItem);
            if(!CollectionUtils.isEmpty(dataCube.getHeaderItems())){
                crossItems.addAll(dataCube.getHeaderItems());
            }
            for(Item item:crossItems){
                dataItem.addDim(item.getDimId(),item.getId());
            }
            try {
                dataKeys.add(dataItem.buildKey());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        //可清理的keys集合
        List<String> removingKeys = new ArrayList<>();
        for(String cubeDataKey:dataCube.getDatas().keySet()){
            if(!dataKeys.contains(cubeDataKey)){
                removingKeys.add(cubeDataKey);
            }
        }
        //删除数据
        for(String removingKey:removingKeys){
            dataCube.getDatas().remove(removingKey);
        }
    }

    /**
     * 立方体维度计算
     *
     * @param dataCube 立方体
     * @param dimId 计算的维度ID
     * @param calculateItems 计算项集合
     * @param refDimId 基于计算维度，建立排名、占比的维度
     * @param refCalculateItems 排名项、占比项等
     * @return
     */
    private DataCube doCalculateDimension(DataCube dataCube,
                                        String dimId,
                                        List<CalculateItem> calculateItems,
                                        String refDimId,
                                        List<CalculateItem> refCalculateItems){
        if(dataCube.getDatas()==null){
            return  dataCube;
        }
        //
        List<Dimension> dimensions = processCalculateDimension(dataCube,dimId,refDimId);
        Dimension calculateDimension = dimensions.get(dimensions.size()-1);//最后一个维度为计算维度
        //
        if(!dimId.equals(calculateDimension.getId())){
            return dataCube;
        }
        //处理计算项
        processCalculateItems(calculateDimension,calculateItems);
        //分块处理维度数据
        processDimensionBlocks(dataCube,dimensions,calculateDimension,refDimId,refCalculateItems);

        //增加关联维度的项
        processRefCalculateItems(dimensions,refDimId,refCalculateItems);


        return dataCube;
    }

    /**
     * 处理占比、排名等关联计算项
     * @param dimensions
     * @param refDimId
     * @param refCalculateItems
     */
    private void processRefCalculateItems(List<Dimension> dimensions,String refDimId, List<CalculateItem> refCalculateItems) {
        if(dimensions.size()>1 && !CollectionUtils.isEmpty(refCalculateItems)){
            Dimension refCalculateDimension = dimensions.get(dimensions.size()-2);
            if(refCalculateDimension.getId().equals(refDimId)){
                refCalculateDimension.getItems().addAll(refCalculateItems);
            }
        }
    }

    /**
     * 处理维度数据块
     */
    private void processDimensionBlocks(DataCube dataCube, List<Dimension> dimensions, Dimension calculateDimension, String refDimId, List<CalculateItem> refCalculateItems) {
        //展开维度为交叉单元格集合
        List<Item>[] crossColItems = CubeDimensionUtils.expendedCrossColItems(dimensions);

        int calculateDimensionCount = calculateDimension.getItems().size();
        //
        List<List<Item>> blockItems = new ArrayList<>();
        for(int i=0;i<crossColItems.length;i++){
            if(i>0 && i%calculateDimensionCount==0){
                //分块执行计算
                calculateBlockItems(dataCube,calculateDimension,blockItems,refDimId,refCalculateItems);
                //新的数组
                blockItems = new ArrayList<>();
            }
            blockItems.add(crossColItems[i]);
        }
        if(blockItems.size()>0){
            //执行最后一块的计算
            calculateBlockItems(dataCube,calculateDimension,blockItems,refDimId,refCalculateItems);
        }
    }

    /**
     * 处理计算项
     * @param calculateDimension
     * @param calculateItems
     */
    private void processCalculateItems(Dimension calculateDimension,List<CalculateItem> calculateItems) {
        //处理外部加入的计算项
        if(!CollectionUtils.isEmpty(calculateItems)){
            List<CalculateItem> convertExpressionCalculateItems = new ArrayList<>(calculateItems);
            for(CalculateItem calculateItem:convertExpressionCalculateItems){
                calculateItem.setExpression(CalculateItemUtils.convertItemExpression(calculateItem));
            }
            calculateDimension.getItems().addAll(convertExpressionCalculateItems);
        }
    }

    /**
     *
     * @param dataCube
     * @param dimId
     * @return
     */
    private List<Dimension> processCalculateDimension(DataCube dataCube,String dimId,String refDimId){
        List<Dimension> dimensions = new ArrayList<>();//维度集合
        Dimension calculateDimension = null,refDimension = null;
        //分离计算维度和其他维度
        for(Dimension dimension: dataCube.getDimensions()){
            dimension.getItems().forEach(item -> item.setDimId(dimension.getId()));
            if(dimId.equals(dimension.getId())){
                calculateDimension = dimension;
            }else if(dimension.getId().equals(refDimId)){
                refDimension = dimension;
            }else{
                dimensions.add(dimension);
            }
        }
        if(refDimension!=null){//关联计算的维度，主要用于排名，占比
            dimensions.add(refDimension);
        }
        //计算维度放最后一个集合元素
        dimensions.add(calculateDimension);
        return dimensions;
    }

    /**
     *
     * @param dataCube
     * @param crossItem
     * @return
     */
    private DataItem parseValueDataItem(DataCube dataCube,List<Item> crossItem){
        List<Item> items = new ArrayList();

        if(!CollectionUtils.isEmpty(dataCube.getHeaderItems())){
            items.addAll(dataCube.getHeaderItems());
        }
        items.addAll(crossItem);
        DataItem dataItem = CalculateItemUtils.buildDataItem(items);
        String dataKey = dataItem.buildKey();

        if(dataCube.getDatas().containsKey(dataKey)){
            dataItem = dataCube.getDatas().get(dataKey);
        }

        if(dataItem.getData()==null){
            DataValue dataValue = new DataValue("0");
            dataValue.setValue(0d);
            dataItem.setData(dataValue);
        }

        if(dataItem.getData().getValue()==null){
            dataItem.getData().setValue(0d);
        }

        return dataItem;
    }
    /**
     * 分块计算
     * @param dataCube
     * @param calculateDimension
     * @param blockItems
     * @param refDimId
     * @param refCalculateItems
     */
    private void calculateBlockItems(DataCube dataCube,Dimension calculateDimension,
                                     List<List<Item>> blockItems,String refDimId,
                                     List<CalculateItem> refCalculateItems) {
        double sum = 0;
        Map<String,Double> values = new HashMap<>();
        List<ValueItem> valueItems = new ArrayList<>();
        int index = 0;
        Map<Item,DataItem> calculateDataItems = new LinkedHashMap<>();
        //把项的健值对添加到values中，
        for(List<Item> crossItem:blockItems){
            //
            Item item = calculateDimension.getItems().get(index++);
            //
            DataItem valueDataItem = parseValueDataItem(dataCube,crossItem);
            Double value = valueDataItem.getData().getValue().doubleValue();
            //计算合计
            sum+=value;
            values.put(item.getId(),value);
            valueItems.add(new ValueItem(item.getId(),value));
            //根据表达式计算
            if(StringUtils.isNotEmpty(item.getExpression())){
                calculateDataItems.put(item,valueDataItem);
            }
        }

        values.put(PARAM_SUM,sum);//分组合计
        values.put(PARAM_ITEM_COUNT,new Integer(calculateDimension.getItems().size()).doubleValue());//维度项数

        //基于维度计算排名和占比
        if(StringUtils.isNotEmpty(refDimId) && !CollectionUtils.isEmpty(refCalculateItems)){
            this.addRefCalculateDatas(dataCube, calculateDimension,blockItems,values,valueItems,refDimId,refCalculateItems);
        }

        //执行分组维度项计算
        if(!CollectionUtils.isEmpty(calculateDataItems)){
            this.doGroupCalculate(dataCube,values,calculateDataItems,refDimId,refCalculateItems);
        }

    }

    /**
     *
     * @param dataCube
     * @param values 用于计算表达式的参数值
     * @param calculateDataItems 计算项集合
     * @param
     */
    private void doGroupCalculate(DataCube dataCube,
                                  Map<String,Double> values,
                             Map<Item,DataItem> calculateDataItems,String refDimId,
                                  List<CalculateItem> refCalculateItems){
        List<Item> calculateItems = new ArrayList(calculateDataItems.keySet());
        Collections.reverse(calculateItems);

        for(Item calculateItem:calculateItems){
            DataItem dataItem = calculateDataItems.get(calculateItem);
            double value = calculateExpressionValue(calculateItem.getExpression(),values);
            DataValue dataValue = new DataValue(CubeDimensionUtils.format(value));
            dataValue.setValue(value);
            values.put(calculateItem.getId(),value);
            dataItem.setData(dataValue);
            //加入到cube中
            dataCube.addDataItem(dataItem);
        }
    }
    //生成排名、占比数据
    private void addRefCalculateDatas(DataCube dataCube,Dimension calculateDimension,
                                      List<List<Item>> blockItems,
                                      Map<String,Double> values,List<ValueItem> valueItems,
                                      String refDimId,List<CalculateItem> calculateItems) {

        Map<String,Integer> rankings = buildRankingMap(valueItems);//

        for(CalculateItem calculateItem:calculateItems){//分组计算项
            //当blockItems中包含item时,增加项
            Item refItem = new Item(calculateItem.getMappedId(),"");
            refItem.setDimId(refDimId);
            //获取数据排名
            int index = 0;
            for(List<Item> crossItem:blockItems){
                Item item = calculateDimension.getItems().get(index++);//计算维度项
                if(containRefItem(crossItem,refItem)){//如果当前块中包含计算项
                    //增加计算项
                    Map<String,Double> refValues = new HashMap<>();
                    refValues.put(PARAM_RANKING,rankings.get(item.getId()).doubleValue());
                    refValues.put(PARAM_PROPORTION,values.get(item.getId())/values.get(PARAM_SUM)*100);
                    //基于表达式计算
                    addRefCalculateData(dataCube,crossItem,calculateItem,refItem,calculateExpressionValue(calculateItem.getExpression(),refValues));
                }
            }
        }
    }

    private boolean containRefItem(List<Item> crossItem, Item refItem) {
        for(Item item :crossItem){
            if(isSameItem(item,refItem)){
                return true;
            }
        }
        return false;
    }

    private boolean isSameItem(Item item,Item refItem){
        return item.getId().equals(refItem.getId()) && item.getDimId().equals(item.getDimId());
    }

    /**
     *
     * @param dataCube
     * @param crossItem
     * @param calculatedValue
     */
    private void addRefCalculateData(DataCube dataCube, List<Item> crossItem, Item calculateItem,Item refItem, double calculatedValue) {
        List<Item> items = new ArrayList<>(crossItem);

        DataItem dataItem = new DataItem();//构建计算数据对象

        items.forEach(item -> {
            if(isSameItem(item,refItem)){//计算项
                dataItem.addDim(item.getDimId(),calculateItem.getId());
            }else{
                dataItem.addDim(item.getDimId(),item.getId());
            }
        });
        //
        DataValue dataValue = new DataValue(CubeDimensionUtils.format(calculatedValue));
        dataValue.setValue(calculatedValue);
        //
        dataItem.setData(dataValue);
        //加入到cube中
        dataCube.addDataItem(dataItem);
    }

    /**
     *
     * @param valueItems
     * @return
     */
    private Map<String,Integer> buildRankingMap(List<ValueItem> valueItems){
        //排序数据
        Collections.sort(valueItems, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        int index = 0;
        Map<String,Integer> rankings = new HashMap<>();
        //排名
        for(ValueItem valueItem:valueItems){
            rankings.put(valueItem.getId(),++index);
        }
        return rankings;
    }
    /**
     *
     * @param expression
     * @param values
     * @return
     */
    private double calculateExpressionValue(String expression, Map<String, Double> values) {
        Expression execExpression = parser.parseExpression(expression);
        StandardEvaluationContext context = new StandardEvaluationContext(values);
        try {
            return execExpression.getValue(context,Double.class);
        } catch (EvaluationException e) {
            //e.printStackTrace();
        }
        return 0d;
    }

}
